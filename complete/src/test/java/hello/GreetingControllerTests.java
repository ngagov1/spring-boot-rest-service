/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hello;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerTests
{

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void noParamGreetingShouldReturnDefaultMessage() throws Exception
	{

		final MvcResult result = this.mockMvc
				.perform(get("/greeting").accept(MediaType.APPLICATION_XML).contentType(MediaType.APPLICATION_XML)).andDo(print())
				.andExpect(status().isOk()).andExpect(xpath("/Greeting/content").string("Hello, World!"))
				.andExpect(xpath("/Greeting/id").number(1d))
				.andExpect(content().contentType(MediaType.APPLICATION_XML_VALUE + ";charset=UTF-8")).andReturn();
		final String content = result.getResponse().getContentAsString();
		assertThat(content, is("<Greeting><id>1</id><content>Hello, World!</content></Greeting>"));
	}

	@Test
	public void paramGreetingShouldReturnTailoredMessage() throws Exception
	{

		final MvcResult result = this.mockMvc.perform(get("/greeting").param("name", "Spring Community")).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.content").value("Hello, Spring Community!")).andReturn();
		final String content = result.getResponse().getContentAsString();
		assertThat(content, is("{\"id\":2,\"content\":\"Hello, Spring Community!\"}"));
	}

}
