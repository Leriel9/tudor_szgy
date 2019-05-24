package hu.elte.szgy.tudor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.net.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.szgy.tudor.TudorApplication;
import hu.elte.szgy.tudor.data.Kerdes;
import hu.elte.szgy.tudor.data.Valasz;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TudorApplication.class)
public class TudorApplicationTests {
    private static Logger log = LoggerFactory.getLogger(TudorApplicationTests.class);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
		log.info("SetUp executing");
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
    }

    @After
    public void tearDown() throws Exception {
    	log.info("TearDown executing");
    }

    private <T> T mapFromJson(String json, Class<T> clazz)
	    throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
    }
        
    @Test
    public void testGetKerdesById() throws Exception {
		String uri = "/kerdes/1";
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Kerdes kerdes = mapFromJson(content, Kerdes.class);
	    log.info("D: " + kerdes.getDatum() + " S: " + kerdes.getSzoveg() + " ID: " + kerdes.getKerdesId() + " C:" + kerdes.getClass());
    	Boolean b = kerdes.getKerdesId() == 1;
		assertTrue(b);
    }

    @Test
    public void testGetValaszById() throws Exception {
		String uri = "/valasz/1";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
			.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Valasz valasz = mapFromJson(content, Valasz.class);
	    log.info("D: " + valasz.getDatum() + " S: " + valasz.getSzoveg() + " ID: " + valasz.getValaszId() + " C:" + valasz.getClass());
    	Boolean b = valasz.getValaszId() == 1;
		assertTrue(b);
    }

    @Test
    public void testListValaszok() throws Exception {
		URI uri = new URI("/valasz/tudor/1");
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
			.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		log.info("GetOsztaly returned: '" + content + "'");
		Valasz[] valaszok = mapFromJson(content, Valasz[].class);
		for(Valasz v : valaszok) {
		    log.info("Id: " +  v.getValaszId() + "V: " + v.getSzoveg() + "C: " + v.getClass());
		}
		assertTrue(valaszok.length > 0);
    }
}