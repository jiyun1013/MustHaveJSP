<%@ page import="fileupload.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String saveDirectory = application.getRealPath("/Uploads"); //저장할 디렉토리
    int maxPostSize = 1024 * 1000; //파일 최대 크기(1MB)
    String encoding = "UTF-8"; //인코딩 방식
    
    try{
    	MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
    	
    	String fileName = mr.getFilesystemName("attachedFile"); //현재 파일 이름
    	String ext = fileName.substring(fileName.lastIndexOf(".")); //파일 확장자
    	String now = new SimpleDateFormat("yyyyMMdd_hMsS").format(new Date());
    	String newFileName = now + ext;
    	
    	File oldFile = new File(saveDirectory + File.separator + fileName);
    	File newFile = new File(saveDirectory + File.separator + newFileName);
    	oldFile.renameTo(newFile);
    	
    	String name = mr.getParameter("name");
    	String title = mr.getParameter("title");
    	String[] cateArray = mr.getParameterValues("cate");
    	StringBuffer cateBuf = new StringBuffer();
    	if(cateArray == null){
    		cateBuf.append("선택 없음");
    	}else{
    		for(String s : cateArray){
    			cateBuf.append(s+", ");
    		}
    	}
    	
    	MyfileDTO dto = new MyfileDTO();
    	dto.setName(name);
    	dto.setTitle(title);
    	dto.setCate(cateBuf.toString());
    	dto.setOfile(fileName);
    	dto.setSfile(newFileName);
    	
    	MyfileDAO dao = new MyfileDAO();
    	dao.insertFile(dto);
    	dao.close();
    	
    	response.sendRedirect("FileList.jsp");
    }catch(Exception e){
    	e.printStackTrace();
    	request.setAttribute("errorMessage", "파일 업로드 오류");
    	request.getRequestDispatcher("FileUploadMain.jsp").forward(request, response);
    }
    %>