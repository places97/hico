package kr.go.hico.cmm.file.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import kr.go.enums.ResCd;
import kr.go.hico.cmm.file.service.FileService;
import kr.go.hico.cmm.file.vo.FileVo;
import kr.go.hico.cmm.core.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

@Controller
@RequestMapping("/file")
public class FileController {

  @Autowired
  private FileService fileService;

  @Value("${file.upload.dir}")
  private String fileDir;

  //테스트용 화면
  @RequestMapping("/uploadForm.do")
  public String uploadForm() {
    return "test/file/uploadForm";
  }

  @RequestMapping("/upload.do")
  public String upload() {
    return "test/file/upload";
  }

  /**
   * 단일 파일 업로드 처리 (AJAX 방식)
   */
  @PostMapping("/upload.do")
  @ResponseBody
  public ResponseEntity<ResultVo> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      FileVo savedFile = fileService.uploadAndSaveFile(file);
      if (savedFile != null) {
        return ResponseEntity.ok(ResultVo.success(savedFile));

      } else {
        return ResponseEntity.ok(ResultVo.fail(ResCd.CM_IO));
        //return ResponseEntity.badRequest().body(ResultVo.fail(ResCd.INVALID_INPUT));
      }
    } catch (IOException e) {
      return ResponseEntity.ok(ResultVo.fail(ResCd.CM_IO));
    }
  }

  /**
   * 멀티 파일 업로드 처리 (AJAX 방식)
   */
  @PostMapping("/upload-multi.do")
  @ResponseBody
  public ResponseEntity<ResultVo> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
    try {
      List<FileVo> savedFilesList = fileService.uploadAndSaveFiles(files);

      return ResponseEntity.ok(ResultVo.success(savedFilesList));

    } catch (IOException e) {
      return ResponseEntity.ok(ResultVo.fail(ResCd.CM_IO));
    }
  }

  /**
   * 단일 파일 업로드 처리 (Form 제출 방식) 삭제예정
   */
  @PostMapping("/upload-formtest.do")
  public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
    try {
      FileVo savedFile = fileService.uploadAndSaveFile(file);
      if (savedFile != null) {
        model.addAttribute("message", "단일 파일 업로드 성공: " + savedFile.getOriginalName());
        model.addAttribute("storedFileName", savedFile.getStoredName());
      }
    } catch (IOException e) {
      model.addAttribute("message", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
      e.printStackTrace();
    }
    return "test/file/uploadForm";
  }

  /**
   * 멀티 파일 업로드 처리 (Form 제출 방식) 삭제예정
   */
  @PostMapping("/upload-multi-formtest.do")
  public String uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files, Model model) {
    try {
      List<FileVo> savedFilesList = fileService.uploadAndSaveFiles(files);

      model.addAttribute("message", savedFilesList.size() + "개의 파일 업로드 완료.");
      model.addAttribute("savedFilesList", savedFilesList);

    } catch (IOException e) {
      model.addAttribute("message", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
      e.printStackTrace();
    }
    return "test/file/uploadForm";
  }

  /**
   * 파일 다운로드 처리
   */
  @GetMapping("/download/{fileName}")
  public void downloadFile(@PathVariable String fileName, HttpServletResponse response) {

    try {
      String filePath = fileDir + File.separator + fileName;
      File file = new File(filePath);

      // 💡 파일 존재 여부 및 읽기 가능 여부 확인
      if (!file.exists() || !file.canRead()) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 에러 반환
        return;
      }

      // 한글 파일명 깨짐 방지를 위한 인코딩 및 헤더 설정
      String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8).replace("..", "");
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"");
      response.setContentLength((int) file.length()); // 파일 크기 설정

      try (InputStream is = new FileInputStream(file);
          OutputStream os = response.getOutputStream()) {

        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
          os.write(buffer, 0, len);
        }
        os.flush();

      } catch (IOException e) {
        // 스트리밍 중 오류 발생 시
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 에러 반환
      }

    } catch (IOException e) {
      // 그 외 파일 경로 오류 등
      try {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      } catch (IOException ioException) {}
    }
  }

  /**
   * 이미지 파일 뷰어 처리
   */
  @GetMapping("/view/{fileName}")
  public void viewImage(@PathVariable String fileName, HttpServletResponse response) {
    try {
      String filePath = fileDir + File.separator + fileName;
      File file = new File(filePath);

      if (!file.exists() || !file.canRead()) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
      }

      // 💡 Content-Type 결정: 파일 확장자를 기반으로 MIME 타입 결정
      String mimeType = URLConnection.guessContentTypeFromName(file.getName());
      if (mimeType == null) {
        mimeType = "image/jpeg"; // 기본값 설정 (예: JPEG)
      }
      response.setContentType(mimeType);

      response.setContentLength((int) file.length());

      try (InputStream is = new FileInputStream(file);
          OutputStream os = response.getOutputStream()) {

        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
          os.write(buffer, 0, len);
        }
        os.flush();

      } catch (IOException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }

    } catch (IOException e) {
      try {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      } catch (IOException ioException) {  }
    }
  }

  /**
   * 단일 파일 업로드 처리 (Form 제출 방식)

  @PostMapping("/upload-formtest.do")
  public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
    if (!file.isEmpty()) {
      Path uploadPath = Paths.get(fileDir);
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath); // 디렉토리가 없으면 생성 (상위 디렉토리 포함)
      }

      String originalFilename = file.getOriginalFilename();
      String storeFilename = UUID.randomUUID().toString() + "_" + originalFilename;
      String filePath = fileDir + File.separator + storeFilename;

      // 파일 저장
      file.transferTo(new File(filePath));

      model.addAttribute("message", "단일 파일 업로드 성공: " + originalFilename);
      model.addAttribute("storedFileName", storeFilename);
    }
    return "test/file/uploadForm";
  }
   */
  /**
   * 멀티 파일 업로드 처리 (Form 제출 방식)

  @PostMapping("/upload-multi-formtest.do")
  public String uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files, Model model) throws IOException {
    Path uploadPath = Paths.get(fileDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    int uploadedCount = 0;
    List<String> storedFileNames = new ArrayList<>();

    for (MultipartFile file : files) {
      if (!file.isEmpty()) {
        String originalFilename = file.getOriginalFilename();
        String storeFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        String filePath = fileDir + File.separator + storeFilename;

        file.transferTo(new File(filePath));
        storedFileNames.add(storeFilename); // 목록에 추가
        uploadedCount++;
      }
    }

    model.addAttribute("message", uploadedCount + "개의 파일 업로드 완료.");
    model.addAttribute("storedFileNames", storedFileNames); // 💡 [추가] 모델에 목록 추가

    return "test/file/uploadForm";
  }
   */
}