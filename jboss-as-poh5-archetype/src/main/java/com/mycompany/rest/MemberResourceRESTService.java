package com.mycompany.rest;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.model.Member;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/members")
@RequestScoped
@Stateful
public class MemberResourceRESTService {
   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<Member> memberEventSrc;

   @GET
   @Produces("text/xml")
   public List<Member> listAllMembers() {
      // Use @SupressWarnings to force IDE to ignore warnings about "genericizing" the results of
      // this query
      @SuppressWarnings("unchecked")
      // We recommend centralizing inline queries such as this one into @NamedQuery annotations on
      // the @Entity class
      // as described in the named query blueprint:
      // https://blueprints.dev.java.net/bpcatalog/ee5/persistence/namedquery.html
      final List<Member> results = em.createQuery("select m from Member m order by m.name").getResultList();
      return results;
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("text/xml")
   public Member lookupMemberById(@PathParam("id") long id) {
      return em.find(Member.class, id);
   }

   @POST
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public Response createMember(@FormParam("name") String name, @FormParam("email") String email, @FormParam("phone") String phone) {
      Response.ResponseBuilder builder = null;

      Member member = new Member();
      member.setName(name);
      member.setEmail(email);
      member.setPhoneNumber(phone);

      try {
         // Validates the Book manually
         Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
         Set<ConstraintViolation<Member>> violations = validator.validate(member);

         //TODO handle validation errors


         //Check the uniqueness of the email address
         //TODO centralize this because the JSF impl needs this too

         //if (!results.isEmpty()) {
         if (emailAlreadyExists(member.getEmail())){
            //throw exception and handle the bad request
            throw new ValidationException("Someones already used this email.");
         }

         log.info("Registering " + member.getName());
         em.persist(member);
         memberEventSrc.fire(member);

         builder = Response.ok();
      } catch (ValidationException e) {
         //TODO Catch existing email exception
         System.out.println("%%%%%%%%%%" + e);

         builder = Response.status(Response.Status.CONFLICT);
         builder.header("error.msg", e.getMessage());
      }

      return builder.build();
   }

   public boolean emailAlreadyExists(String value) {
      Query checkEmailExists = em.createQuery(" SELECT COUNT(b.email) FROM Member b WHERE b.email=:emailparam");
      checkEmailExists.setParameter("emailparam", value);
      long matchCounter = 0;
      matchCounter = (Long) checkEmailExists.getSingleResult();
      if (matchCounter > 0) {
         return true;
      }
      return false;
   }
}
