package lt.vu.persistence;

import lt.vu.entities.Member;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;


@ApplicationScoped
public class MembersDAO {

    @Inject
    private EntityManager em;

    public void persist(Member member){
        this.em.persist(member);
    }

    public void merge(Member member){
        this.em.merge(member);
    }
    public Member findOne(Integer id){
        return em.find(Member.class, id);
    }

    public Member update(Member member){
        return em.merge(member);
    }

    public List<Member> loadAll() {
        return em.createNamedQuery("Member.findAll", Member.class).getResultList();
    }

    public void updateProductConcurrently(Integer memberId) {
        for(int i=0;i<100000;i++) {
            Member member1 = em.find(Member.class, memberId);
            Member member2 = em.find(Member.class, memberId);

            member1.setRank(100);
            member2.setRank(i);

            em.merge(member1);
            em.merge(member2);
        }
    }
}
