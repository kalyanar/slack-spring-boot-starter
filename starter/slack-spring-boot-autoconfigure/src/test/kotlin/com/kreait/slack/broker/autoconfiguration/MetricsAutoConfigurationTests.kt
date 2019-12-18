package com.kreait.slack.broker.autoconfiguration

import com.kreait.slack.broker.broker.CommandBroker
import com.kreait.slack.broker.broker.InteractiveComponentBroker
import com.kreait.slack.broker.metrics.CommandMetricsCollector
import com.kreait.slack.broker.metrics.InteractiveComponentMetricsCollector
import io.micrometer.core.instrument.MeterRegistry
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.test.context.FilteredClassLoader

@DisplayName("Test Installation Auto configuration")
class MetricsAutoConfigurationTests {

    @Nested
    @DisplayName("Command Metrics AutoConfiguration")
    class CommandMetricsTests {

        @DisplayName("CommandMetrics Registration")
        @Test
        fun commandMetricsRegistration() {
            TestApplicationContext.base()
                    .withSystemProperties(
                            "slack.installation.error-redirect-url:http://localhost:8080/installation/error",
                            "slack.installation.success-redirect-url:http://localhost:8080/installation/success"
                    )
                    .withConfiguration(AutoConfigurations.of(SlackBrokerAutoConfiguration::class.java, TeamStoreAutoconfiguration::class.java, WebMvcAutoConfiguration::class.java))
                    .run {
                        Assertions.assertDoesNotThrow { it.getBean(CommandMetricsCollector::class.java) }
                    }
        }

        @DisplayName("CommandBroker Registration Without Micrometer")
        @Test
        fun commandMetricsRegistrationWithoutMicrometer() {
            TestApplicationContext.base()
                    .withSystemProperties(
                            "slack.installation.error-redirect-url:http://localhost:8080/installation/error",
                            "slack.installation.success-redirect-url:http://localhost:8080/installation/success"
                    )
                    .withClassLoader(FilteredClassLoader(MeterRegistry::class.java))
                    .withConfiguration(AutoConfigurations.of(SlackBrokerAutoConfiguration::class.java, TeamStoreAutoconfiguration::class.java, WebMvcAutoConfiguration::class.java))
                    .run {
                        Assertions.assertThrows(NoSuchBeanDefinitionException::class.java) { it.getBean(CommandMetricsCollector::class.java) }
                        Assertions.assertDoesNotThrow { it.getBean(CommandBroker::class.java) }
                    }

        }
    }

    @Nested
    @DisplayName("Command Metrics AutoConfiguration")
    class InteractiveComponentMetricsTests {

        @DisplayName("CommandMetrics Registration")
        @Test
        fun interactiveComponentMetricsRegistration() {
            TestApplicationContext.base()
                    .withSystemProperties(
                            "slack.installation.error-redirect-url:http://localhost:8080/installation/error",
                            "slack.installation.success-redirect-url:http://localhost:8080/installation/success"
                    )
                    .withConfiguration(AutoConfigurations.of(SlackBrokerAutoConfiguration::class.java, TeamStoreAutoconfiguration::class.java, WebMvcAutoConfiguration::class.java))
                    .run {
                        Assertions.assertDoesNotThrow { it.getBean(CommandMetricsCollector::class.java) }
                    }
        }

        @DisplayName("CommandBroker Registration Without Micrometer")
        @Test
        fun interactiveComponentMetricsRegistrationWithoutMicrometer() {
            TestApplicationContext.base()
                    .withSystemProperties(
                            "slack.installation.error-redirect-url:http://localhost:8080/installation/error",
                            "slack.installation.success-redirect-url:http://localhost:8080/installation/success"
                    )
                    .withClassLoader(FilteredClassLoader(MeterRegistry::class.java))
                    .withConfiguration(AutoConfigurations.of(SlackBrokerAutoConfiguration::class.java, TeamStoreAutoconfiguration::class.java, WebMvcAutoConfiguration::class.java))
                    .run {
                        Assertions.assertThrows(NoSuchBeanDefinitionException::class.java) { it.getBean(InteractiveComponentMetricsCollector::class.java) }
                        Assertions.assertDoesNotThrow { it.getBean(InteractiveComponentBroker::class.java) }
                    }

        }
    }
}
