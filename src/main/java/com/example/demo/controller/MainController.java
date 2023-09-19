package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.AccountDAO;
import com.example.demo.dao.ReportDAO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Inspect;
import com.example.demo.entity.Report;
import com.example.demo.entity.Role;
import com.example.demo.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

@Setter
@Controller
public class MainController {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AccountDAO a_dao;
	@Autowired
	private RestTemplate restTemplate; // RestTemplate을 주입
	@Autowired
	private ReportDAO r_dao;

	@Autowired
	private AccountService as;

	@GetMapping("/main")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView("/main");
//		System.out.println("main 로드됨");
		return mav;
	}

	@GetMapping("/report")
	public ModelAndView report() {
		ModelAndView mav = new ModelAndView("/report");
		return mav;
	}

	@GetMapping("/login")
	public ModelAndView loginform() {
		ModelAndView mav = new ModelAndView("/login");

		return mav;
	}

	@GetMapping("/join")
	public ModelAndView join(Model model) {
		ModelAndView mav = new ModelAndView("/join");
		return mav;
	}

	@GetMapping("/inspect")
	public void inspect() {

	}

	@PostMapping("/upload")
    public ResponseEntity<String> uploadImages( Inspect inspect) {
        try {
            MultipartFile[] files = inspect.getUploadfile();

            // 이미지를 MultiValueMap으로 포장
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            for (int i = 0; i < files.length; i++) {
                map.add("image" + i, files[i]);
            }

            // Flask 서버로 파일들을 전송
            String flaskServerUrl = "http://localhost:5001/process-images";
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskServerUrl, map, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String result = responseEntity.getBody();
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 처리 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 중 오류가 발생했습니다.");
        }
    }
	
	@PostMapping("/inspect")
	public String inspectImage(@RequestParam("image1") MultipartFile image1,
	                           @RequestParam("image2") MultipartFile image2,
	                           Model model) {
	    try {
	        // Flask 애플리케이션의 URL 설정
	        String flaskUrl = "http://localhost:5001/process-images"; // Flask 애플리케이션의 URL로 변경하세요.

	        // JSON 요청 데이터를 Flask로 전송
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("image1", new FileSystemResource(convert(image1)));
	        body.add("image2", new FileSystemResource(convert(image2)));
	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

	        // RestTemplate을 사용하여 Flask 엔드포인트로 POST 요청 전송
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);
	        String jsonResponse = responseEntity.getBody();

	        // JSON 데이터를 모델에 추가하여 HTML 템플릿에서 사용
	        model.addAttribute("flaskData", jsonResponse);

	        return "result"; // result.html 템플릿을 반환
	    } catch (Exception e) {
	        // 오류 처리 로직 추가
	        return "error"; // error.html 템플릿을 반환
	    }
	}

	// MultipartFile을 File로 변환하는 함수 (임시 디렉토리에 저장)
	private File convert(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    file.transferTo(convFile);
	    return convFile;
	}


	@PostMapping("/report")
	public ModelAndView report(Report r) {
		ModelAndView mav = new ModelAndView("/main");
		r.setRno((int) System.currentTimeMillis());
		r_dao.save(r);
		System.out.println("추가성공");
		return mav;

	}

	@PostMapping("/join")

	public ModelAndView join(Account a, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("redirect:/login");
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		String regdate = today.format(formatter);
		a.setRole(Role.USER);
		a.setRegdate(regdate);
		a.setPwd(passwordEncoder.encode(a.getPwd()));
		a_dao.save(a);
		return mav;

	}

	private UserDetails createUserDetails(String id) {
		List<GrantedAuthority> authorities = new ArrayList();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		// System.out.println("롤부여했니 ?");
		return new User(id, a_dao.findByAid(id).getPwd(), authorities);
	}

}
