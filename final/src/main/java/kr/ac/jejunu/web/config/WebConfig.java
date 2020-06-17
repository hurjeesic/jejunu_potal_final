package kr.ac.jejunu.web.config;

import kr.ac.jejunu.web.sequence.MyInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import java.util.List;

/*
	dispatcher-servlet.xml에 대한 정의
 */
@Configuration
@EnableWebMvc // <annotation-driven />
@ComponentScan("kr.ac.jejunu") // <context:component-scan base-package="kr.ac.jejunu" />
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		<welcome-file-list>
//			<welcome-file>/WEB-INF/views/index.jsp</welcome-file>
//		</welcome-file-list>
		registry.addViewController("/").setViewName("forward:WEB-INF/views/index.jsp");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		<interceptors>
//			<interceptor>
//				<mapping path="/**/*" />
//				<beans:bean class="kr.ac.jejunu.web.sequence.MyInterceptor" />
//			</interceptor>
//		</interceptors>
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**/*");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		<resources mapping="/**" location="/WEB-INF/static/" />
		registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/static/");
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//		<beans:property name="contentNegotiationManager">
//			<beans:bean class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
//				<beans:property name="mediaTypes">
//					<beans:props>
//						<beans:prop key="js">application/json</beans:prop>
//						<beans:prop key="x">application/xml</beans:prop>
//					</beans:props>
//				</beans:property>
//			</beans:bean>
//		</beans:property>
		configurer.mediaType("js", MediaType.APPLICATION_JSON)
				.mediaType("x", MediaType.APPLICATION_XML);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
//		<beans:property name="defaultViews">
//			<beans:list>
//				<beans:bean class="org.springframework.web.servlet.view.json.MappingJackson2JsonView" />
//				<beans:bean class="org.springframework.web.servlet.view.xml.MappingJackson2XmlView" />
//			</beans:list>
//		</beans:property>
		registry.enableContentNegotiation(new MappingJackson2JsonView());
		registry.enableContentNegotiation(new MappingJackson2XmlView());

//		<beans:bean name="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
//			<beans:property name="prefix" value="/WEB-INF/views/" />
//			<beans:property name="suffix" value=".jsp" />
//		</beans:bean>
		registry.jsp().prefix("/WEB-INF/views/")
				.suffix(".jsp");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
	}
}