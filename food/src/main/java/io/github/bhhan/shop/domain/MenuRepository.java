package io.github.bhhan.shop.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long>, CustomMenuRepository {
}
