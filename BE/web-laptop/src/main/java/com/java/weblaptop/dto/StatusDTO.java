package com.java.weblaptop.dto;

import com.java.weblaptop.entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatusDTO {
    private long status_id;

    @NotNull
    private long status;

    @NotNull
    private String statusName;


    public StatusDTO convertToDto(Status status) {
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setStatus_id(status.getStatus_id());
        statusDTO.setStatus(status.getStatus());
        statusDTO.setStatusName(status.getStatusName());

        return statusDTO;
    }

    public Status convertToEti(StatusDTO statusDTO) {
        Status status = new Status();

        status.setStatus(statusDTO.getStatus());
        status.setStatusName(statusDTO.getStatusName());

        return status;
    }


    public List<StatusDTO> toListDto(List<Status> listEntity) {
        List<StatusDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }

}
