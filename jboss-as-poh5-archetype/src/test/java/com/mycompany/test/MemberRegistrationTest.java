package com.mycompany.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.mycompany.rest.MemberResourceRESTService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mycompany.model.Member;
import com.mycompany.util.Resources;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {
   @Deployment
   public static Archive<?> createTestArchive() {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(Member.class, MemberResourceRESTService.class, Resources.class)
            .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
   }

   @Inject
   MemberResourceRESTService memberRegistration;

   @Inject
   Logger log;

   @Test
   public void testRegister() throws Exception {
      Response response = memberRegistration.createMember("Jane Doe", "jane@mailinator.com", "2125551234");

      assertTrue(response.getStatus() == 200);
      log.info(" New member was persisted and returned status " + response.getStatus());
   }

   @Test
   public void testInvalidRegister() throws Exception {
      Response response = memberRegistration.createMember("", "", "");

      assertTrue(response.getStatus() == 400);
      assertTrue(response.getEntity() != null);
      assertTrue(((HashMap<String, String>)response.getEntity()).size() == 3);
      log.info("Invalid member register attempt failed with return code " + response.getStatus());
   }

   @Test
   public void testDuplicateEmail() throws Exception {
      //Register an initial user
      memberRegistration.createMember("Jane Doe", "jane@mailinator.com", "2125551234");

      //Register a different user with the same email
      Response response = memberRegistration.createMember("John Doe", "jane@mailinator.com", "2133551234");

      assertTrue(response.getStatus() == 409);
      assertTrue(response.getEntity() != null);
      assertTrue(((HashMap<String, String>)response.getEntity()).size() == 1);
      log.info("Duplicate member register attempt failed with return code " + response.getStatus());
   }
   
}
