package com.hospital.app.Hospital.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListResponseDto {
	private List<?> content;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;
}