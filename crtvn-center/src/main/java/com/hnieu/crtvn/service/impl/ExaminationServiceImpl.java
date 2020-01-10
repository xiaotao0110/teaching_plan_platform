package com.hnieu.crtvn.service.impl;

import com.hnieu.crtvn.dao.*;
import com.hnieu.crtvn.dto.ExaminationDTO;
import com.hnieu.crtvn.entity.*;
import com.hnieu.crtvn.service.IExamService;
import com.hnieu.crtvn.service.IExaminationService;
import com.hnieu.crtvn.util.Reduction;
import com.hnieu.crtvn.vo.DownloadExamVO;
import com.hnieu.crtvn.vo.DownloadExaminationVO;
import com.hnieu.crtvn.vo.ExamVO;
import com.hnieu.crtvn.vo.ExaminationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ExaminationServiceImpl implements IExaminationService {

	private static final int EXAM_ALL = 0; //0:表示全部
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private IClassroomDAO classroomDAO;

	@Autowired
	private IProfessionDAO professionDAO;

	@Autowired
	private ICourseDAO courseDAO;

	@Autowired
	private IStudentDAO studentDAO;
	
	@Autowired
	private IEtimeDAO etimeDAO;

	@Autowired
	private ITeacherDAO teacherDAO;
	
	@Autowired
	private IExaminationDAO examinationDAO;
	
	@Autowired
	private IExamDAO examDAO;
	
	@Autowired
	private IExamService examService;
	
	@Autowired
	private Reduction reduction;
	
	@Override
	public Map<String,String> automatic(ExaminationDTO examinationDTO) throws Exception {
		Map<String,String> result = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();

		HttpSession session = request.getSession();
		Academician academician = (Academician) session.getAttribute("academician");
		
		//考场信息
		List<Examination> examinationList = new ArrayList<>();
		
		/**
		 * 是否重新生成考场信息
		 */
		if(examinationDTO.getRenew() == 1){
			/**
			 * 如果已安排，不需要安排
			 */
			params.clear();
			params.put("mark", examinationDTO.getMark());
			List<Examination> examinationBean = new ArrayList<>();
			examinationBean = examinationDAO.findExaminationByMark(params);
			if(examinationBean.size()!=0 ){
				result.put("msg", "已生成考场信息");
				return result;
			}else{
				reduction.revert();
			}

		}else if(examinationDTO.getRenew() == 2){ //重新
			reduction.revert();
		}
		
		/**
		 * 1、统计当前学院可用考场的座位数
		 */
		params.clear();
		params.put("collegeId", academician.getCollege().getId());
		params.put("mark", 1); // 考场状态为1
		params.put("status", 2);//未安排
		//取升序，合理分配考场
		List<Classroom> classroomList = classroomDAO.findClassroomSeatsCount(params);
		long number = 0;
		for (int i = 0; i < classroomList.size(); i++) {
			number += classroomList.get(i).getNumber();
		}

		/**
		 * 2、统计当前学院每个课程的考试人数 即专业人数
		 */
		// 1)统计专业
		params.clear();
		params.put("collegeId", academician.getCollege().getId());
		List<Profession> professionList = professionDAO.findProfession(params);
		List<Course> courseList = null;
		
		// 2)统计每个专业人数 、设置课程人数
		Map<Integer, Integer> pSNMap = new HashMap<>();
		List<Integer> pSNList = new ArrayList<>();
		Integer professionId = 0;
		params.clear();
		
		long maxCourseId = courseDAO.getMaxCourseId();
		for(int i=0;i<maxCourseId + 1;i++){
			pSNList.add(i, 0);
		}
		
		for (int i = 0; i < professionList.size(); i++) {
			//统计每个专业人数 
			professionId = professionList.get(i).getId();
			params.put("professionId", professionId);		
			params.put("mark", 2); //未安排的	
			params.put("manner", 1); //闭卷
			//设置课程人数
			courseList = courseDAO.findCourseByPId(params);
			params.clear();
			for(int j=0;j<courseList.size();j++){
				params.put("professionId", professionId);
				pSNMap.put(courseList.get(j).getId(), studentDAO.findStudentCount(params).size());				
				pSNList.remove(courseList.get(j).getId());
				pSNList.add(courseList.get(j).getId(),studentDAO.findStudentCount(params).size());
			}		
		}
		
		/**
		 * 3、选择考试人数最接近座位数的（不同专业的一个或多个未排课程）组合
		 * (默认选择不同专业最多和次最多的考试人数的考场，前提是人数不大于座位数； 如果都一样，按降序取)同时考，并选择时间(未使用的，取升序)。
		 */
						
		// 已选课程
		List<Integer> sList = new ArrayList<>();
		//当前安排的课程
		List<Integer> indexList = new ArrayList<>();
						
		if(Collections.max(pSNList) > number){ //座位数大于最大值，异常
			throw new Exception("座位数不足");
		}		
		
		//时间
		params.clear();
		params.put("mark", 2);
		List<Etime> etimeList = etimeDAO.findEtime(params);
		Etime etime = null;
				
		int sum = 0,count = 0;;
		for (int j = 0; j < pSNList.size(); j++) {			
			if(sList.contains(j) || pSNList.get(j)==0){
				continue;
			}else{
				sum = pSNList.get(j);
				indexList.add(j);
				sList.add(j);
			}			
			if(sum == number){
				etime = etimeList.get(count++);
				examinationList.addAll(assCId(pSNMap, pSNList,indexList,etime));
				indexList.clear();
				break;
			}	
			
			//过滤同专业
			params.clear();
			params.put("courseId", j);
			professionId = professionDAO.findProfessionByCourseId(params).getId();
			params.clear();
			params.put("professionId", professionId);
			params.put("manner", 1); //闭卷
			courseList = courseDAO.findCourseByPId(params);		
			int index = 0;
			//备份
			List<Integer> pSNListCopy = new  ArrayList<>();
			pSNListCopy.addAll(pSNList);				
			for(int i=0;i<courseList.size();i++){
				index = courseList.get(i).getId();
				pSNListCopy.remove(index);
				pSNListCopy.add(index, 0);
			}
			//已选专业
			List<Integer> pList = new ArrayList<>();	
			for (int k = pSNListCopy.size() - 1 ; k >= 0; k-- ) {				
				if(sList.contains(k) || pSNListCopy.get(k)==0 ){
					continue;
				}else{
					params.clear();
					params.put("courseId", k);
					professionId = professionDAO.findProfessionByCourseId(params).getId();
					//过滤同专业   
					if(pList.contains(professionId)){
						continue;
					}else{
						pList.add(professionId);
					}
					sum += pSNListCopy.get(k);
				}	
				if (sum >= number) {
					break;
				}
				
				indexList.add(k);
				sList.add(k);
			}
			etime = etimeList.get(count++);
			examinationList.addAll(assCId(pSNMap, pSNList,indexList,etime));
			indexList.clear();
		}	
		
		/*
		 * 4、分配考场，根据科目人数，选择座位数最接近考试人数的未使用考场（一个或多个）
		 */
		long maxClassroomId = classroomDAO.getMaxClassroomId();
		//可用考场   index-->座位数
		List<Integer> roomList = new ArrayList<>();
		for(int i=0;i<maxClassroomId + 1;i++){
			roomList.add(i, 0);
		}		
		for(int i=0;i<classroomList.size();i++){
			int id = classroomList.get(i).getId();
			roomList.remove(id);
			roomList.add(id,classroomList.get(i).getNumber());
		}
			
		//已分配时间
		List<Integer> tIdList = new ArrayList<>();
		for(int i=0;i<examinationList.size();i++){
			int tid = examinationList.get(i).getEtime().getId();
			if(!tIdList.contains(tid)){
				tIdList.add(tid);
			}
		}
		//已分配时间对应的考试科目
		Map<Integer,List<Integer>> tMap = new HashMap<>(); 
		for(int i=0;i<tIdList.size();i++){
			List<Integer> cid = new ArrayList<>();
			for(int j=0;j<examinationList.size();j++){
				int tid = examinationList.get(j).getEtime().getId() ;
				if(tIdList.get(i) == tid){
					cid.add(examinationList.get(j).getCourse().getId());
				}
			}
			tMap.put(tIdList.get(i), cid);
		}
		
		//已分配考场
		List<Integer> crid = null;
		for (Entry<Integer, List<Integer>> entry : tMap.entrySet()) { 
			crid = new ArrayList<>();
			List<Integer> cids = entry.getValue();  //当前需要分配考场的科目
			List<Integer> numberList = new ArrayList<>();//考试科目对应的人数
			List<Integer> minList = new ArrayList<>(); //用于排序，取最小值
			
			for(int i=0;i<Collections.max(cids) + 1;i++){
				numberList.add(i, 0);
			}
			for(int i=0;i<cids.size();i++){
				int id = cids.get(i);
				int minNumber = getStudentNumberByCourseId(id).size();
				numberList.remove(id);
				numberList.add(id,minNumber);
				minList.add(minNumber);
			}
			
			
			Collections.sort(minList);
			
			for(int i=0;i<minList.size();i++){
				int minNumber = minList.get(i);
				sum = 0;		
				//当前分配的教室Id
				indexList = new ArrayList<>();
				for(int j=0;j<roomList.size();j++){
					if(roomList.get(j) == 0 || crid.contains(j)){ //过滤已选
						continue;
					}
					sum += roomList.get(j);
					if(sum < minNumber){
						indexList.add(j);
						crid.add(j);
					}else{
						indexList.add(j);
						crid.add(j);  //大于的最小值
						break;
					}
				}				
				int index = numberList.indexOf(minNumber);
				//开始分配考场
				List<Examination> bean = assCRid(examinationList, index, indexList);  //i-->考场Id
				examinationList.clear();				
				examinationList.addAll(bean);
				numberList.remove(index);
				numberList.add(index, 0);
			}			
		}
		
		/*
		 * 5、分配监考老师(未监考的老师)，如果本学院老师未分配完，不分配其他学院老师
		 */
		params.clear();
		params.put("collegeId", academician.getCollege().getId());
		params.put("mark", 1);//监考老师		
		params.put("status", 2);//未安排		
		List<Teacher> teacherList = teacherDAO.findTeacherByStatus(params);
				
		//已分配时间对应的考试科目
		Map<Integer,List<Integer>> ttMap = new HashMap<>(); 
		for(int i=0;i<tIdList.size();i++){
			List<Integer> cmid = new ArrayList<>();
			for(int j=0;j<examinationList.size();j++){
				int tid = examinationList.get(j).getEtime().getId() ;
				if(tIdList.get(i) == tid){
					cmid.add(examinationList.get(j).getClassroom().getId());
				}
			}
			ttMap.put(tIdList.get(i), cmid);
		}
		
		List<Integer> cmList = null; //同一时间所有考场
		List<Integer> ttList = null; //已选教师
		for (Entry<Integer, List<Integer>> entry : ttMap.entrySet()) {
			cmList = entry.getValue(); 
			int tid = entry.getKey();
			Examination examination = null;
			ttList = new ArrayList<>();
			for(int i=0;i<cmList.size();i++){
				for(int j=0;j<examinationList.size();j++){
					examination = examinationList.get(j);
					if(cmList.get(i) == examination.getClassroom().getId() 
							&& tid == examination.getEtime().getId()){						
						//分配教师
						count = 2;  //每个考场两个老师
						for(int k=0;k<teacherList.size();k++){
							int id = teacherList.get(k).getId();					
							if(!ttList.contains(id)){							
								Teacher teacher = teacherDAO.findTeacherById(id);
								if(count == 2){
									examination.setTeacherByOnetid(teacher);
									count--;
								}else if(count == 1){
									examination.setTeacherByTwotid(teacher);
									count--;
								}
								teacher.setStatus(1);//已分配
								teacherDAO.updateTeacher(teacher);
																
								ttList.add(id);
								if(count == 0){
									break;
								}
							}
						}
						break;
					}
				}		
			}	
		}
		
		/**
		 * 6、考场安排成功、生成考场信息详情
		 */
		Examination examination = null;
		Classroom classroom = null;
		Profession profession = null;
		for(int i=0;i<examinationList.size();i++){
			examination = examinationList.get(i);
			//考场号
			classroom = classroomDAO.findClassroomById(examination.getClassroom().getId());
			examination.setCode(classroom.getCode());
			
			//状态  自动
			examination.setMark(1);
			
			//人数
			examination.setNumbers(classroom.getNumber());
			
			//专业
			params.clear();
			params.put("courseId", examination.getCourse().getId());
			profession = professionDAO.findProfessionByCourseId(params);
			examination.setProfession(profession);
			
			examinationDAO.saveExamination(examination);
		}
		
		result.put("msg", "生成考场信息成功");
		return result;
	}

	
	private List<Examination> assCRid(List<Examination> examinationList,Integer courseId, List<Integer> crid){
		
		List<Examination> examList = new ArrayList<>();
		Examination examination = null;
		for(int i=0;i<examinationList.size();i++){			
			if(courseId == examinationList.get(i).getCourse().getId()){
				examination = examinationList.get(i);				
				for(int j=0;j<crid.size();j++){
					Examination bean = new Examination();
					bean.setEtime(examination.getEtime());
					bean.setCourse(examination.getCourse());
					
					Classroom  classroom = classroomDAO.findClassroomById(crid.get(j));					
					bean.setClassroom(classroom);
						
					classroom.setStatus(1); //已安排
					classroomDAO.updateClassroom(classroom);
					
					examList.add(j, bean);
				}
				examinationList.remove(i);
			}			
		}
		examList.addAll(examinationList);
		
		return  examList;
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
	
	
	private List<Examination> assCId(Map<Integer, Integer> pSNMap,List<Integer> pSNList,
			List<Integer> indexList,Etime etime){
		List<Examination> examinationList = new ArrayList<>();
		
		Examination examination = null ;	
		Course course = null;
		for(int i=0;i<indexList.size();i++){			
			examination = new Examination();
			
			//设置时间
			examination.setEtime(etime);
			
			//设置课程
			List<Integer> courseIds = getCIdKey(pSNMap,pSNList.get(indexList.get(i)));
			for(int j=0;j<courseIds.size();j++){
				int g = indexList.get(i);
				int h = courseIds.get(j);
				if(g == h){
					course = courseDAO.findCourseById(courseIds.get(j));
					break;
				}
			}
			examination.setCourse(course);	
			
			//设置已用
			etime.setMark(1);
			course.setMark(1);
			etimeDAO.updateEtime(etime);
			courseDAO.updateCourse(course);
			
			examinationList.add(examination);
		}
		return examinationList ;
	}
	
	private List<Integer> getCIdKey(Map<Integer, Integer> map, Integer value) {
		List<Integer> keyList = new ArrayList<>();
		for (Integer key : map.keySet()) {
			if (map.get(key).equals(value)) {
				keyList.add(key);
			}
		}
		return keyList;
	}

	
	/**
	 * 以上为核心代码，暂不更改
	 */

	@Override
	public Map<String, Object> findExaminationByPage(int pageNo, int pageSize, ExaminationDTO examinationDTO) {
		
		List<Examination> examinations = examinationDAO.findExaminationByPage(pageNo, pageSize, getExaminationParams(examinationDTO));

		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("total",examinationDAO.findExaminationCountByGrid(getExaminationParams(examinationDTO)));
		resultMap.put("rows", getExaminationVOList(examinations));
        
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> findExamTeacherSelect(ExaminationDTO examinationDTO) {
		Map<String,Object> params = new HashMap<String,Object>();		

		params.put("mark", examinationDTO.getMark());
		
		if(examinationDTO.getProfessionId() != EXAM_ALL){
			params.put("professionId", examinationDTO.getProfessionId());
		}
		List<Examination> examinationList = examinationDAO.findExaminationByMark(params);
		
		List<ExaminationVO> examinationVOs = new ArrayList<>();
		Teacher teacher = null;
		int tid ;
		ExaminationVO  vo = null;
		for(int i=0;i<examinationList.size();i++){
			if(examinationDTO.getStatus() == 1){
				tid = examinationList.get(i).getTeacherByOnetid().getId();
				teacher = teacherDAO.findTeacherById(tid);
				vo = new ExaminationVO();
				vo.setTeacherId1(teacher.getId());
				vo.setTeacherName1(teacher.getName());
				examinationVOs.add(vo);
			}else if(examinationDTO.getStatus() == 2){
				tid = examinationList.get(i).getTeacherByTwotid().getId();
				teacher = teacherDAO.findTeacherById(tid);
				vo = new ExaminationVO();
				vo.setTeacherId2(teacher.getId());
				vo.setTeacherName2(teacher.getName());
				examinationVOs.add(vo);
			}			
		}
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> examinationVOMap = new HashMap<String,Object>();
		
		examinationVOMap.put("text","全部");
		examinationVOMap.put("id",0);
		list.add(examinationVOMap);
		
		for(int i=0; i < examinationVOs.size(); i++){				
			if(examinationDTO.getStatus() == 1){
				examinationVOMap = new HashMap<String,Object>();
				examinationVOMap.put("text",examinationVOs.get(i).getTeacherName1());
				examinationVOMap.put("id",examinationVOs.get(i).getTeacherId1());
				
			}else if(examinationDTO.getStatus() == 2){
				examinationVOMap = new HashMap<String,Object>();
				examinationVOMap.put("text",examinationVOs.get(i).getTeacherName2());
				examinationVOMap.put("id",examinationVOs.get(i).getTeacherId2());		
			}					
			list.add(examinationVOMap);
	    }		
		return list;
	}
	
	private Map<String, Object> getExaminationParams(ExaminationDTO examinationDTO){
		HttpSession session = request.getSession();
		Academician academician = (Academician) session.getAttribute("academician");
		
		Map<String,Object> params = new HashMap<String,Object>();
			
		params.put("mark",examinationDTO.getMark() );
		params.put("collegeId", academician.getCollege().getId());
		
		if(examinationDTO.getProfessionId() != EXAM_ALL){
			params.put("professionId", examinationDTO.getProfessionId());
		}
		if(examinationDTO.getCourseId() != EXAM_ALL){
			params.put("courseId", examinationDTO.getCourseId());
		}
		if(examinationDTO.getTeacherId1() != EXAM_ALL){
			params.put("teacherId1",examinationDTO.getTeacherId1());
		}
		if(examinationDTO.getTeacherId2() != EXAM_ALL){
			params.put("teacherId2",examinationDTO.getTeacherId2());
		}
		
		return params;
	}
	
	private List<ExaminationVO> getExaminationVOList(List<Examination> examinations){
		//返回的视图
		List<ExaminationVO> resultList = new ArrayList<>();
		ExaminationVO  vo = null;
		Examination examination = null ;
		Profession profession = null;
		Course course = null;
		Classroom classroom = null;
		Etime etime = null;
		Teacher teacher = null;
		for(int i=0;i<examinations.size();i++){
			examination = examinations.get(i);
			vo = new ExaminationVO();
			
			vo.setExaminationId(examination.getId());
			
			vo.setExaminationCode(examination.getCode());
			
			profession = getProfessionByCourseId(examination.getCourse().getId());
			vo.setProfessionId(profession.getId());
			vo.setProfessionName(profession.getName());
			
			course = courseDAO.findCourseById(examination.getCourse().getId());
			vo.setCourseId(course.getId());
			vo.setCourseCode(course.getCode());
			vo.setCourseName(course.getName());
			
			classroom = classroomDAO.findClassroomById(examination.getClassroom().getId());
			vo.setClassroomId(classroom.getId());
			vo.setClassroomCode(classroom.getCode());
			
			//监考老师 
			teacher = teacherDAO.findTeacherById(examination.getTeacherByOnetid().getId());
			vo.setTeacherName1(teacher.getName());
			teacher = teacherDAO.findTeacherById(examination.getTeacherByTwotid().getId());
			vo.setTeacherName2(teacher.getName());
						
			//时间
			etime = etimeDAO.findEtimeById(examination.getEtime().getId());
			vo.setStime(etime.getStime());
			vo.setEtime(etime.getEtime());
			
			vo.setNumber(examination.getNumbers());
			
			resultList.add(vo);
		}
		return resultList;

		
	}
	
	private Profession getProfessionByCourseId(Integer courseId){
		
		Map<String, Object> params = new HashMap<String, Object>();		
		params.put("courseId", courseId);		
		return professionDAO.findProfessionByCourseId(params);
	}


	@Override
	public void manual(ExaminationDTO examinationDTO) {
			
		Map<String,Object> params = new HashMap<>();	
		
		 String[] classroomArr = examinationDTO.getClassrooms().split(",");
		 List<Integer> classroomIdList = new ArrayList<>();
		 for(int i=0;i<classroomArr.length;i++){
			 classroomIdList.add(Integer.parseInt(classroomArr[i]));
		 }		 
		 List<Classroom> classroomList = new ArrayList<>();
		 Classroom classroom = new Classroom();
		 for(int i=0;i<classroomIdList.size();i++){
			 classroom = classroomDAO.findClassroomById(classroomIdList.get(i));
			 classroomList.add(classroom); 			 
		 }
		 
		 String[] teacherArr = examinationDTO.getTeachers().split(",");
		 List<Integer> teacherIdList = new ArrayList<>();
		 for(int i=0;i<teacherArr.length;i++){
			 teacherIdList.add(Integer.parseInt(teacherArr[i]));
		 }		 
		 List<Teacher> teacherList = new ArrayList<>();
		 Teacher teacher = new Teacher();
		 for(int i=0;i<teacherIdList.size();i++){
			 teacher = teacherDAO.findTeacherById(teacherIdList.get(i));
			 teacherList.add(teacher); 			 
		 }
		 
		 Course course = courseDAO.findCourseById(examinationDTO.getCourseId());
		 
		 Etime etime = etimeDAO.findEtimeById(examinationDTO.getEtimeId());
			
		 params.clear();
		 params.put("courseId", examinationDTO.getCourseId());		
		 Profession profession = professionDAO.findProfessionByCourseId(params);		 
		 
		 List<Integer> sList = new ArrayList<>(); //已分配老师
		 int count=2; 
		 
		 Examination examination = null;
		 for(int i=0;i<classroomList.size();i++){
			 examination = new Examination();
			 classroom = classroomList.get(i);
			 //考场号
			 examination.setCode(classroom.getCode());
			 
			 //专业
			 examination.setProfession(profession);
			 
			 //课程
			 examination.setCourse(course);
			 
			 //教室
			 examination.setClassroom(classroom);
			 
			 //时间
			 examination.setEtime(etime);
			 
			 //教师一，二
			 for(int j=0;j<teacherList.size();j++){
				 teacher = teacherList.get(j);
				 if(sList.contains(teacher.getId())){
					continue; 
				 }
				 if(count == 2){
					examination.setTeacherByOnetid(teacher);					
					count--;
					sList.add(teacher.getId());
				 }else if(count == 1){
					 examination.setTeacherByTwotid(teacher);			
					 count--;
					 sList.add(teacher.getId());
				 }else{
					 count=2;
					 break; 
				 }
			 }
			 
			 //状态
			 examination.setMark(examinationDTO.getMark());
			 
			 //人数
			 examination.setNumbers(classroom.getNumber());	 
			 			 
			 examinationDAO.saveExamination(examination);
		 }	
		 
		 //同步数据库
		 course.setMark(1);
		 courseDAO.updateCourse(course);
		 
		 for(int i=0;i<classroomList.size();i++){
			 classroom = classroomList.get(i);
			 classroom.setStatus(1);
			 classroomDAO.updateClassroom(classroom);
		 }
		
		 etime.setMark(1);
		 etimeDAO.updateEtime(etime);
		 
		 for(int j=0;j<teacherList.size();j++){
			 teacher = teacherList.get(j);
			 teacher.setStatus(1);
			 teacherDAO.updateTeacher(teacher);
		 }

	}


	@Override
	public List<DownloadExaminationVO>  exportExcelExamination(ExaminationDTO examinationDTO) {
		
		List<Examination> examinationList = examinationDAO.findExamination(getExaminationParams(examinationDTO));

		List<ExaminationVO> examinations = getExaminationVOList(examinationList);

		//准备数据
		List<DownloadExaminationVO> downloadExaminationVOs = new ArrayList<>();
		DownloadExaminationVO downloadExaminationVO = null;
		ExaminationVO examinationVO = null;
		for(int i=0;i<examinations.size();i++){
			examinationVO = examinations.get(i);
			downloadExaminationVO = new DownloadExaminationVO(
					examinationVO.getExaminationCode(), examinationVO.getProfessionName(), 
					examinationVO.getCourseCode(), examinationVO.getCourseName(), 
					examinationVO.getClassroomCode(), examinationVO.getTeacherName1(),
					examinationVO.getTeacherName2(), examinationVO.getStime(), 
					examinationVO.getEtime(), examinationVO.getNumber());
			downloadExaminationVOs.add(downloadExaminationVO);
		}
		
		return downloadExaminationVOs;

	}


	@Override
	public List<DownloadExamVO> exportExcelExam(ExaminationDTO examinationDTO) {
		Map<String,Object> params = new HashMap<>();
		
		List<DownloadExamVO> downloadExamVOs = new ArrayList<>();
		List<Exam> exams = null;
		if(examinationDTO.getExaminationId() != null && examinationDTO.getExaminationId() != 0){
			params.put("examinationId", examinationDTO.getExaminationId());
			exams = examDAO.findExamByEId(params);
			
			return getDownloadExamVOs(exams);
		}else{
			
			List<Examination> examinationList = examinationDAO.findExamination(getExaminationParams(examinationDTO));
			Examination examination = null;
			for(int i=0;i<examinationList.size();i++){
				examination = examinationList.get(i);
				params.clear();
				params.put("examinationId", examination.getId());
				exams = examDAO.findExamByEId(params);
				downloadExamVOs.addAll(getDownloadExamVOs(exams));
				exams = null;
			}
			return downloadExamVOs;
		}	
			
		
	}

	private  List<DownloadExamVO> getDownloadExamVOs(List<Exam> exams){
		 List<DownloadExamVO> downloadExamVOs = new ArrayList<>();
		 DownloadExamVO downloadExamVO = null;
		 
		 List<ExamVO> examVOs = examService.getExamVOList(exams);
		 ExamVO examVO = null;
		 for(int i=0;i<examVOs.size();i++){
			 examVO = examVOs.get(i);
			 downloadExamVO = new DownloadExamVO(
					 examVO.getSid(), examVO.getStudentName(), 
					 examVO.getClasssCode(), examVO.getCourseName(),
					 examVO.getExaminationCode(), examVO.getExaminationTime(), 
					 examVO.getSeatNo());
			 downloadExamVOs.add(downloadExamVO);
		 }
		 
		 return downloadExamVOs;
	}
}
