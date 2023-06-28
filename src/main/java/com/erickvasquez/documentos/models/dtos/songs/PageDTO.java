package com.erickvasquez.documentos.models.dtos.songs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO <T> {
    private List<T> content;
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
}