package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.*;
import com.hnieu.crtvn.dto.ExamDTO;
import com.hnieu.crtvn.entity.*;
import com.hnieu.crtvn.service.IExamService;
import com.hnieu.crtvn.vo.ExamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ExamServiceImpl implements IExamService {
	
	private static final int EXAM_ALL = 0; //0:表示全部
	
	@Autowired
	private IProfessionDAO professionDAO;

	@Autowired
	private IStudentDAO studentDAO;
	
	@Autowired
	private IExaminationDAO examinationDAO;
	
	@Autowired
	private IClasssDAO classsDAO;
	
	@Autowired
	private IExamDAO examDAO;
	
	@Autowired
	private ICourseDAO courseDAO;
	
	@Autowired
	private IEtimeDAO etimeDAO;
	
	@Override
	public Map<String,String> exam(ExamDTO  examDTO) throws Exception{
		
		Map<String,String> result = new HashMap<>();
		
		Map<String, Object> params = new HashMap<String, Object>();

		List<Examination> examinationList = new ArrayList<>();
		if(examDTO.getMark() == 1){//自动排考	
			//考场信息
			params.put("mark", examDTO.getMark()); 
			examinationList = examinationDAO.findExaminationByMark(params);
			
			//是否生成考场信息
			if(examinationList.size() == 0 ){
				result.put("msg", "请先自动排考！");
				return result;
			}

			
			/**
			 * 验证是否已生成,事务一致性
			 */
			params.clear();
			params.put("examinationId", examinationList.get(0).getId());
			List<Exam> examBean = new ArrayList<>();
			examBean = examDAO.findExamByEId(params);
			if(examBean.size()!=0 ){
				result.put("msg", "已生成考试信息");
				return result;
			}
		}else if(examDTO.getMark() == 2){//手动排考
			//考场信息
			params.put("mark", examDTO.getMark()); 
		    examinationList = examinationDAO.findExaminationByMark(params);
			
			//是否生成考场信息
			if(examinationList.size() == 0 ){
				result.put("msg", "请先自动排考！");
				return result;
			}
			
		}

		
		/**
		 * 7、根据考场信息(科目，教室座位数)，将学习该科目的学生按学号依次排座位号、 生成考试信息详情
		 */		
		params.clear();
		List<Integer> sidList = new ArrayList<>(); //已分配学生
		int coid ,cmNumber;
		List<Student> studentList = null;
		Exam exam = null;
		Student student = null ;
		Classs classs = null;
		Examination examination = null ;
		int count = 0; //统计考场实际人数
		Map<Integer,Integer> countMap = new HashMap<>();
		int countNumber = 0;
 		for(int i=0;i<examinationList.size();i++){
			examination = examinationList.get(i);
			coid = examination.getCourse().getId();
			cmNumber = examination.getClassroom().getNumber();
			studentList = getStudentNumberByCourseId(coid);
			
			//删除缺考学生
			params.clear();
			params.put("courseId", coid);
			params.put("mark", 2);
			List<Studentselection> sl = studentDAO.findStudentselection(params);
			for(int f=0;f<sl.size();f++){
				for(int b=0;b<studentList.size();b++){
					if(sl.get(f).getStudent().getId() == studentList.get(b).getId()){
						studentList.remove(studentList.get(b));
					}
				}			
			}
			
			
			for(int j=0;j<cmNumber && studentList.size()>0 ;j++){
				exam = new Exam();
				//设置考场
				exam.setExamination(examination);				
				for(int k=0;k<studentList.size();k++){
					student = studentList.get(k);
					if(!sidList.contains(student.getId())){
						
						count++;
						
						//设置学生
						exam.setStudent(student);
						
						//设置班级
						int id = student.getClasss().getId();
						classs = classsDAO.findClasssById(id);	
					
						exam.setClasss(classs);
						
						//座位号
						exam.setSeatNo(j+1);
						
						sidList.add(student.getId());
						studentList.remove(student);
						examDAO.saveExam(exam);
						break;
					}
				}	
			}
			countNumber += count;
			if(count != cmNumber){ //考场实际人数
				examination.setNumbers(count);
				examinationDAO.updateExamination(examination);				
			}
			count=0;
			
			//同专业的不同课程重新分配学生
			if(!countMap.containsKey(coid)){
				studentList = getStudentNumberByCourseId(coid);
				countMap.put(coid, studentList.size());
			}else{
				
				if(countMap.get(coid) == countNumber){
					studentList = getStudentNumberByCourseId(coid);
					for(int s=0;s<studentList.size();s++){
						for(int g=0;g<sidList.size();g++){
							if(studentList.get(s).getId() == sidList.get(g)){
								sidList.remove(sidList.get(g));
								break;
							}
						}						
					}
					countNumber = 0;
				}				
			}
		}
		
		result.put("msg", "生成考试信息成功");
		return result;
		
	}

	private List<Student> getStudentNumberByCourseId(Integer courseId){
		int professionId ;
		Map<String, Object> params = new HashMap<String, Object>();		
		
		params.put("courseId", courseId);		
		professionId = professionDAO.findProfessionByCourseId(params).getId();
		
		params.clear();
		params.put("professionId", professionId);		
		
		return studentDAO.findStudentCount(params);
	}

	@Override
	public Map<String, Object> findExamByPage(int pageNo, int pageSize, ExamDTO examDTO) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("examinationId", examDTO.getExaminationId());	
		
		List<Exam> examList = examDAO.findExamByPage(pageNo, pageSize, params);
			
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",examDAO.findExamCountByGrid(params));
		resultMap.put("rows", getExamVOList(examList));
        
		
		return resultMap;
	}

	@Override
	public List<ExamVO> getExamVOList(List<Exam> examList){
		List<ExamVO> resultList = new ArrayList<>();
		Exam exam = null;
		Student student = null;
		ExamVO examVO = null;
		Classs classs = null;
		Course course = null;
		Examination examination = null;
		Etime etimeBean = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date stime = null;
		Date etime = null;
		StringBuffer timeStr = null;
		for(int i=0;i<examList.size();i++){
			exam = examList.get(i);
			examVO = new ExamVO();
			timeStr = new StringBuffer();
			
			examination = examinationDAO.findExaminationById(exam.getExamination().getId());
			
			//学号 ,名字
			student = studentDAO.findStudentById(exam.getStudent().getId());
			examVO.setSid(student.getSid());
			examVO.setStudentName(student.getName());
			
			//班级
			classs = classsDAO.findClasssById(exam.getClasss().getId());
			examVO.setClasssCode(classs.getCode());
			
			//课程
			course = courseDAO.findCourseById(examination.getCourse().getId());
			examVO.setCourseName(course.getName());
			
			//考场号			
			examVO.setExaminationCode(examination.getCode());
			
			//时间
			etimeBean = etimeDAO.findEtimeById(examination.getEtime().getId());
			stime = etimeBean.getStime();
			etime = etimeBean.getEtime();
			timeStr.append(sdf.format(stime)).append("--").append(sdf.format(etime));
			examVO.setExaminationTime(timeStr.toString());
			
			//座位号
			examVO.setSeatNo(exam.getSeatNo());
				
			resultList.add(examVO);
		}
		return resultList;
		
	}
}
