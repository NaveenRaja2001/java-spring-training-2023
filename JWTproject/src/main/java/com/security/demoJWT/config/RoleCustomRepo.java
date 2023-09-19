package com.security.demoJWT.config;

import com.security.demoJWT.user.Roles;
import com.security.demoJWT.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;


    public List<Roles> getRole(User user){
        StringBuilder sql =new StringBuilder()
                .append("SELECT r.name as name \n" +
                        "FROM user u\n" +
                        "JOIN user_roles ur ON u.id=ur.user_id\n" +
                        "JOIN roles r ON r.id=ur.roles_id\n"+
                        "Where 1=1");
        if(user.getEmail()!=null){
            sql.append(" and email=:email");
        }
        NativeQuery<Roles> query=((Session)entityManager.getDelegate()).createNativeQuery(sql.toString());
        if(user.getEmail()!= null){
            query.setParameter("email",user.getEmail());
        }
        query.addScalar("name", StandardBasicTypes.STRING);
        query.setResultTransformer(Transformers.aliasToBean(Roles.class));
        return query.list();
    }

}
