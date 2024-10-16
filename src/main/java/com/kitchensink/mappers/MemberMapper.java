package com.kitchensink.mappers;

import com.kitchensink.dto.MemberDTO;
import com.kitchensink.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper MAPPER = Mappers.getMapper(MemberMapper.class);
    MemberDTO toDTO(Member member);
    Member toModel(MemberDTO customerDTO);
}
