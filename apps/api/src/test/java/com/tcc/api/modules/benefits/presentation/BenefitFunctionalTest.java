package com.tcc.api.modules.benefits.presentation;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tcc.api.modules.benefits.domain.BenefitFrequency;
import com.tcc.api.modules.benefits.domain.BenefitRepository;
import com.tcc.api.modules.benefits.presentation.dtos.BenefitCreateRequestDTO;
import com.tcc.api.modules.users.domain.User;
import com.tcc.api.modules.users.domain.UserRepository;
import com.tcc.api.modules.users.domain.UserRole;

@SpringBootTest(
    properties = {
        "spring.datasource.url=jdbc:h2:mem:tcc_benefits_test_db;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.flyway.enabled=true"
    }
)
public class BenefitFunctionalTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BenefitRepository benefitRepository;

    private UUID testInstitutionId;
    private Principal testPrincipal;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        testInstitutionId = UUID.randomUUID();
        testPrincipal = () -> "gestor@ifsul.edu.br";

        User manager = User.builder()
                .name("Gestor IFSul")
                .email("gestor@ifsul.edu.br")
                .password("Senha@123")
                .cpf("01234567890")
                .phone("51999999999")
                .profilePicture("profile.png")
                .role(UserRole.MANAGER)
                .institutionId(testInstitutionId)
                .build();
        
        userRepository.save(manager);
    }

    @Test
    @DisplayName("Should create benefit successfully via POST /benefits and save it in database")
    void shouldCreateBenefitAndPersistSuccessfully() throws Exception {
        BenefitCreateRequestDTO request = new BenefitCreateRequestDTO(
            "Auxilio Alimentacao",
            "Auxilio para refeicoes no refri",
            "food",
            BigDecimal.valueOf(250.00),
            true,
            1,
            LocalTime.of(12, 0),
            BenefitFrequency.MONTHLY
        );

        mockMvc.perform(post("/benefits")
                .principal(testPrincipal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Auxilio Alimentacao"))
                .andExpect(jsonPath("$.active").value(true));

        boolean exists = benefitRepository.existsByNameAndInstitutionId("Auxilio Alimentacao", testInstitutionId);
        assertTrue(exists, "The benefit should be successfully saved in the database!");
    }
}
