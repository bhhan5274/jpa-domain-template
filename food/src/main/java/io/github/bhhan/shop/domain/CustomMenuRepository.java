package io.github.bhhan.shop.domain;

import java.util.Optional;

public interface CustomMenuRepository {
    Optional<Menu> findByIdOptimisticMode(Long id);
    Optional<Menu> findByIdFetchJoin(Long id);
}
