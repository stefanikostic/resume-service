package com.elevateresume.resume_service.service;

import com.elevateresume.resume_service.dto.BulletPointInsertDTO;
import com.elevateresume.resume_service.dto.ResumeInsertDTO;
import com.elevateresume.resume_service.dto.SectionInsertDTO;
import com.elevateresume.resume_service.entity.SectionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeProcessor {

    public ResumeInsertDTO processResume() {

        // extract sections
        // deepSeekService.extractResumeSections();
        // process Resume
        List<SectionInsertDTO> sectionInsertDTOS = new ArrayList<>();
        SectionInsertDTO sectionInsertDTO = new SectionInsertDTO(SectionType.EDUCATION, "Education", "September 2016",
                "June 2021", "Bachelor of computer engineering\n" + "Ss. Cyril and Methodius University – Skopje, Faculty of Computer Science and Engineering", new ArrayList<>());
        sectionInsertDTOS.add(sectionInsertDTO);
        List<BulletPointInsertDTO> bulletPointInsertDTOS = new ArrayList<>();
        BulletPointInsertDTO bulletPointInsertDTO1 = new BulletPointInsertDTO(null, "Enhanced mobile applications that facilitate communication between smartphones and hearing aids, benefiting \n" +
                "approximately 500k+ users by improving their accessibility and overall experience.");
        BulletPointInsertDTO bulletPointInsertDTO2 = new BulletPointInsertDTO(null, " Reduced significant amount of boilerplate code and enhanced performance around 20% in terms of rendering UI by \n" +
                "transitioning legacy applications from XML layout to Jetpack Compose.");
        bulletPointInsertDTOS.add(bulletPointInsertDTO1);
        bulletPointInsertDTOS.add(bulletPointInsertDTO2);

        SectionInsertDTO workSection = new SectionInsertDTO(SectionType.WORK_EXPERIENCE, "Inscale/WS Audiology, Skopje – Android Developer, Kotlin", "December 2021", "Present", null, bulletPointInsertDTOS);

        sectionInsertDTOS.add(workSection);
        ResumeInsertDTO resumeInsertDTO = new ResumeInsertDTO("Andreja Zivkovic", sectionInsertDTOS);

        return resumeInsertDTO;
    }

    /*
    *  @Override
    public void mapResumeFileToResume(MultipartFile resumeFile) {
        Resume resume = new Resume();
//        deepSeekService.extractResumeSections();
        // process Resume
        // extract sections
        // save to S3
        resumeRepository.save(resume);
    }*/
}
