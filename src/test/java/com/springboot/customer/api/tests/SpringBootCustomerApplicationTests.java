package com.springboot.customer.api.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.springbooot.customer.api.dto.CustomerDTO;
import com.springboot.customer.api.SpringBootCustomerApplication;
import com.springboot.customer.api.tests.DBConfig.H2DBConfig;
import com.springboot.customer.api.utils.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootCustomerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
@Import({

		H2DBConfig.class })
@AutoConfigureMockMvc

public  class SpringBootCustomerApplicationTests {

	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Gson gson;

	@Test
	public void testCreateCustomerWithMockMVC() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setEmail("jaym1111111@test.com");
		customerDTO.setCuser("jay1222265");
		customerDTO.setFirstName("jay");
		customerDTO.setLastName("Mevada");		
		customerDTO.setPassword(Utils.getEncrpt("abc@123"));
		this.mockMvc
				.perform(post("/customers").content(gson.toJson(customerDTO)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("jay")));
	}

	@Test
	public void testUpdateCustomerWithMockMVC() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setEmail("jaym333@test.com");
		customerDTO.setCuser("jay133");
		customerDTO.setFirstName("Jay133");
		customerDTO.setLastName("test333");
		customerDTO.setPassword(Utils.getEncrpt("abc@123"));
		this.mockMvc
				.perform(put("/customers").content(gson.toJson(customerDTO)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Jay133")));
	}

	@Test
	public void testDeleteCustomerWithMockMVC() throws Exception {

		this.mockMvc.perform(delete("/customers/Jaym1@test.com").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	@Sql(scripts = "/commonScript/cleanTable.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/commonScript/data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	public void testCreateRetrieveWithMockMVC() throws Exception {

		this.mockMvc.perform(get("/customers/Jaym1@test.com").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().string(containsString("Jay1")));
	}

	
}
