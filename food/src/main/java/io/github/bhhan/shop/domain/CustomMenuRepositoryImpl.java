package io.github.bhhan.shop.domain;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomMenuRepositoryImpl implements CustomMenuRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Menu> findByIdOptimisticMode(Long id) {
        return Optional.ofNullable(entityManager.find(Menu.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT));
    }

    @Override
    public Optional<Menu> findByIdFetchJoin(Long id) {
        try {
            return Optional.of(entityManager
                    .createQuery("select distinct m from Menu m join fetch m.optionGroupSpecs where m.id = :menuId", Menu.class)
                    .setParameter("menuId", id)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
