package com.elevateresume.resume_service.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "resumes")
@NoArgsConstructor
@Getter
@Setter
public class ResumeDocument {

    @Id
    private String id;

    private String userId;
    private String title;
    private ResumeTemplate template = ResumeTemplate.ORIGINAL;
    private String fileUrl;
    private List<Section> sections;

    public static class Section {
        private String type;
        private List<String> content;
    }
}
