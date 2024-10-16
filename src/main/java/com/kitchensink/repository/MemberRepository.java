package com.kitchensink.repository;

import com.kitchensink.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
