package br.com.projetoweb.repository;

import br.com.projetoweb.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    //@Query(value = "select * from my_schema.my_table where (jsonb_1 @> cast(:jsonbValue as jsonb) " +
    //        "or jsonb_2 @> cast(:jsonbValue as jsonb)) and (status != :badStatus)", nativeQuery = true)
}
