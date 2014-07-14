<#assign project_id="gs-mobile-site-preference">
This guide walks you through the process of using Spring to provide a user with the choice of viewing a normal, mobile, or tablet version of your site, regardless of what type of device being used.

What you'll build
-----------------

You will create a Spring Web MVC application that detects the type of device that is accessing your web site and enables functionality to override the default view for the device by selecting an alternate site preference.

What you'll need
----------------

 - About 15 minutes
 - <@prereq_editor_jdk_buildtools/>


## <@how_to_complete_this_guide jump_ahead='Create a configuration class'/>


<a name="scratch"></a>
Set up the project
------------------

<@build_system_intro/>

<@create_directory_structure_hello/>


<@create_both_builds/>

<@bootstrap_starter_pom_disclaimer/>


<a name="initial"></a>
Create a configuration class
----------------------------

Use the following configuration class to tell Spring where it can find the endpoint controller class:

    <@snippet path="src/main/java/hello/SitePreferenceConfiguration.java" prefix="complete"/>

This class subclasses [`WebMvcConfigurerAdapter`], which allows you to customize the configuration of a Spring MVC application. In this case, you add three classes, [`DeviceResolverHandlerInterceptor`], [`SitePreferenceHandlerInterceptor`] and [`SitePreferenceMethodArgumentResolver`]. [`DeviceResolverHandlerInterceptor`] is an implementation of a [`HandlerInterceptor`] which, as the name implies, intercepts a request to the application and determines the type of requesting device. After the device is resolved, the [`SitePreferenceMethodArgumentResolver`] allows Spring MVC to use the resolved [`SitePreference`] object in a controller method.

Internally, [`DeviceResolverHandlerInterceptor`] examines the `User-Agent` header in the incoming request, and based on the header value, determines whether the request is coming from a normal (desktop) browser, a mobile (phone) browser, or a tablet browser. [`DeviceResolverHandlerInterceptor`] offers several entry points for overriding or customizing how a device is resolved.

[`SitePreferenceHandlerInterceptor`] utilizes the detected device to determine a user's initial site preference. If the user prefers a different site, then that site preference and be used in subsequent requests to override the resolved device value. The site preference is set by passing a specific query string parameter in a request. Once received, the preference is persisted in a cookie for future reference. 


Create a web controller
-----------------------

In Spring, web endpoints are simply Spring MVC controllers. The following Spring MVC controller handles a GET request and returns a String indicating the type of the device:

    <@snippet path="src/main/java/hello/GreetingController.java" prefix="complete"/>

For this example, rather than rely on a view (such as JSP) to render model data in HTML, this controller simply returns the data to be written directly to the body of the response. In this case, the data is a String that reads "Hello NORMAL site preference!" if the requesting client is a desktop browser. The [`@ResponseBody`] annotation tells Spring MVC to write the returned object into the response body, rather than to render a model into a view.


Make the application executable
-------------------------------

Although it is possible to package this service as a traditional [WAR][u-war] file for deployment to an external application server, the simpler approach demonstrated in the next section creates a _standalone application_. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. And along the way, you use Spring's support for embedding the [Tomcat][u-tomcat] servlet container as the HTTP runtime, instead of deploying to an external instance.

### Create an application class

    <@snippet path="src/main/java/hello/Application.java" prefix="complete"/>

The `main()` method defers to the [`SpringApplication`][] helper class, providing `Application.class` as an argument to its `run()` method. This tells Spring to read the annotation metadata from `Application` and to manage it as a component in the _[Spring application context][u-application-context]_.

The [`@ComponentScan`] annotation tells Spring to search recursively through the `hello` package and its children for classes marked directly or indirectly with Spring's [`@Component`] annotation. This directive ensures that Spring finds and registers the `SitePreferenceConfiguration` and `SitePreferenceController` classes, because they are marked with [`@Controller`], which in turn is a kind of [`@Component`] annotation.

The [`@EnableAutoConfiguration`] annotation switches on reasonable default behaviors based on the content of your classpath. For example, because the application depends on the embeddable version of Tomcat (tomcat-embed-core.jar), a Tomcat server is set up and configured with reasonable defaults on your behalf. And because the application also depends on Spring MVC (spring-webmvc.jar), a Spring MVC [`DispatcherServlet`] is configured and registered for you — no `web.xml` necessary! Auto-configuration is a powerful, flexible mechanism. See the [API documentation][`@EnableAutoConfiguration`] for further details.

<@build_an_executable_jar_subhead/>

<@build_an_executable_jar_with_both/>

<@run_the_application_with_both module="service"/>

Logging output is displayed. The service should be up and running within a few seconds.


Test the service
----------------

To test the application, open the following link in a browser. In a normal desktop browser, you should see something like this:

[http://localhost:8080/site-preference](http://localhost:8080/site-preference)

![The initial response for a normal desktop browser](images/initial-request.png)

By passing the `site_preference=mobile` querystring parameter, a user can specify a preference for the mobile site. For example, your site may expose a link from the home page. The link below includes the parameter to change your site preference.

[http://localhost:8080/site-preference?site_preference=mobile](http://localhost:8080/site-preference?site_preference=mobile)

![The response for a mobile browser](images/set-mobile-preference.png)

The site preference is now stored in a cookie and subsequent requests to the site will respect the new preference, without the need of a querystring parameter.

[http://localhost:8080/site-preference](http://localhost:8080/site-preference)

![The response for a tablet browser](images/respect-mobile-preference.png)


Summary
-------

Congratulations! You have just developed a simple web page that detects the type of device being used by a client and offers the option to prefer a different version of the site.


<@u_war/>
<@u_tomcat/>
<@u_application_context/>
[`@Configuration`]:http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/context/annotation/Configuration.html
[`WebMvcConfigurerAdapter`]:http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurerAdapter.html
[`DeviceResolverHandlerInterceptor`]:http://docs.spring.io/spring-mobile/docs/1.1.x/api/org/springframework/mobile/device/DeviceResolverHandlerInterceptor.html
[`SitePreferenceHandlerInterceptor`]:http://docs.spring.io/spring-mobile/docs/1.1.x/api/org/springframework/mobile/device/site/SitePreferenceHandlerInterceptor.html
[`SitePreferenceMethodArgumentResolver`]:http://docs.spring.io/spring-mobile/docs/1.1.x/api/org/springframework/mobile/device/site/SitePreferenceHandlerMethodArgumentResolver.html
[`HandlerInterceptor`]:http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/web/servlet/HandlerInterceptor.html
[`Device`]:http://docs.spring.io/spring-mobile/docs/1.1.x/api/org/springframework/mobile/device/Device.html
[`@ComponentScan`]: http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/ComponentScan.html
[`@Component`]: http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/stereotype/Component.html
[`@ResponseBody`]:http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html
[`SpringApplication`]: http://docs.spring.io/spring-boot/docs/0.5.0.M4/api/org/springframework/boot/SpringApplication.html
[`DispatcherServlet`]: http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html
[`@EnableAutoConfiguration`]: http://docs.spring.io/spring-boot/docs/0.5.0.M4/api/org/springframework/boot/autoconfigure/EnableAutoConfiguration.html
