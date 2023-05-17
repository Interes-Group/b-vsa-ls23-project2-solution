package sk.stuba.fei.uim.vsa.pr2.model;

import java.util.List;

/**
 * Rozhranie stránky entít. Strana obsahuje zoznam vrátených entít metódou pre vyhľadanie entity.
 * Trieda taktiež obsahuje objekt Pageable pre zachytenie informácii o vrátenej stráne. Atribúty
 * totalElements a totalPages je nutné nastaviť na základe vrátených entít.
 * Implementácia musí obsahovať prázdny konštruktor.
 */
public interface Page<R> {

    List<R> getContent();

    Pageable getPage();

}
