package br.com.brunomateus.gestao_vagas.modules.company;


import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.brunomateus.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.brunomateus.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.brunomateus.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.brunomateus.gestao_vagas.utils.Utils;

    @RunWith(SpringRunner.class)
    @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
    @ActiveProfiles("test")
    public class CreateJobControllerTest {
        private MockMvc mvc;
        @Autowired
        private WebApplicationContext context;
        @Autowired
        private CompanyRepository companyRepository;
        @Before
        public void setup(){
            mvc = MockMvcBuilders.webAppContextSetup(this.context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
        }
        
        @Test
        public void should_be_able_to_create_a_new_job() throws Exception{
         
        var company = CompanyEntity.builder()
        .description("Test description")
        .email("test@email.com")
        .name("teste name")
        .password("1234567890")
        .username("test_username")
        .website("test website")
        .build();

        company = companyRepository.saveAndFlush(company);
        
        var createJobDTO =  CreateJobDTO.builder()
            .benefits("test benefits")
            .description("test description")
            .level("test level")
            .build();

        var token = Utils.GenerateToken(company.getId(),"javagas@123#");
            

        this.mvc.perform(
            MockMvcRequestBuilders.post("/job/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Utils.ObjectToJson(createJobDTO))
            .header("Authorization",token)
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception{
            var createJobDTO =  CreateJobDTO.builder()
            .benefits("test benefits")
            .description("test description")
            .level("test level")
            .build();

        var token = Utils.GenerateToken(UUID.randomUUID(),"javagas@123#");
            

        this.mvc.perform(
            MockMvcRequestBuilders.post("/job/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Utils.ObjectToJson(createJobDTO))
            .header("Authorization",token)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }
