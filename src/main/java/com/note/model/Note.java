package com.note.model;
import com.note.rest.ResponseDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note extends ResponseDefault {
    private Long id;
    private String title;
    private String content;
}