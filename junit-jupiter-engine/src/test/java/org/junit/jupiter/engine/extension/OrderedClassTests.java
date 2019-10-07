package org.junit.jupiter.engine.extension;

import org.junit.jupiter.api.*;
import org.junit.jupiter.engine.AbstractJupiterTestEngineTests;
import org.junit.jupiter.engine.extension.OrderedClassTests.OrderAnnotationWithNestedClassTestCase.FirstNestedTestCase;
import org.junit.jupiter.engine.extension.OrderedClassTests.OrderAnnotationWithNestedClassTestCase.FirstNestedTestCase.FirstDoublyNestedTestCase;
import org.junit.jupiter.engine.extension.OrderedClassTests.OrderAnnotationWithNestedClassTestCase.FirstNestedTestCase.SecondDoublyNestedTestCase;
import org.junit.jupiter.engine.extension.OrderedClassTests.OrderAnnotationWithNestedClassTestCase.FirstNestedTestCase.ThirdDoublyNestedTestCase;
import org.junit.jupiter.engine.extension.OrderedClassTests.OrderAnnotationWithNestedClassTestCase.SecondNestedTestCase;
import org.junit.jupiter.engine.extension.OrderedClassTests.OrderAnnotationWithNestedClassTestCase.ThirdNestedTestCase;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.testkit.engine.EngineExecutionResults;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;
import static org.junit.platform.testkit.engine.EventConditions.*;

class OrderedClassTests extends AbstractJupiterTestEngineTests{

    @Test
    void orderAnnotationOnNestedClasses() {
        LauncherDiscoveryRequest request = request().selectors(selectClass(OrderAnnotationWithNestedClassTestCase.class)).build();
        EngineExecutionResults executionResults = executeTests(request);

        // @formatter:on
        executionResults.allEvents().assertEventsMatchExactly(
                event(engine(), started()),
                    event(container(OrderAnnotationWithNestedClassTestCase.class), started()),
                        event(nestedContainer(FirstNestedTestCase.class), started()),
                            event(test("nestedTest1_1"), started()), event(test("nestedTest1_1"), finishedSuccessfully()),
                            event(test("nestedTest1_2"), started()), event(test("nestedTest1_2"), finishedSuccessfully()),
                            event(test("nestedTest1_3"), started()), event(test("nestedTest1_3"), finishedSuccessfully()),
                            event(nestedContainer(FirstDoublyNestedTestCase.class), started()),
                                event(test("nestedTest1_1_1"), started()), event(test("nestedTest1_1_1"), finishedSuccessfully()),
                            event(nestedContainer(FirstDoublyNestedTestCase.class), finishedSuccessfully()),
                            event(nestedContainer(SecondDoublyNestedTestCase.class), started()),
                                event(test("nestedTest1_2_1"), started()), event(test("nestedTest1_2_1"), finishedSuccessfully()),
                            event(nestedContainer(SecondDoublyNestedTestCase.class), finishedSuccessfully()),
                            event(nestedContainer(ThirdDoublyNestedTestCase.class), started()),
                                event(test("nestedTest1_3_1"), started()), event(test("nestedTest1_3_1"), finishedSuccessfully()),
                            event(nestedContainer(ThirdDoublyNestedTestCase.class), finishedSuccessfully()),
                        event(nestedContainer(FirstNestedTestCase.class), finishedSuccessfully()),
                        event(nestedContainer(SecondNestedTestCase.class), started()),
                            event(test("nestedTest2_1"), started()), event(test("nestedTest2_1"), finishedSuccessfully()),
                        event(nestedContainer(SecondNestedTestCase.class), finishedSuccessfully()),
                        event(nestedContainer(ThirdNestedTestCase.class), started()),
                            event(test("nestedTest3_1"), started()), event(test("nestedTest3_1"), finishedSuccessfully()),
                        event(nestedContainer(ThirdNestedTestCase.class), finishedSuccessfully()),
                    event(container(OrderAnnotationWithNestedClassTestCase.class), finishedSuccessfully()),
                event(engine(), finishedSuccessfully()));
        // @formatter:off
    }


    /* TODO: ?? @NestedClassOrder(MethodOrderer.OrderAnnotation.class) */
    static class OrderAnnotationWithNestedClassTestCase {

        @Nested
        @Order(1)
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
        /* TODO: ?? @NestedClassOrder(MethodOrderer.OrderAnnotation.class) */
        class FirstNestedTestCase {

            @Test
            @Order(1)
            void nestedTest1_1() {
            }

            @Test
            @Order(2)
            void nestedTest1_2() {
            }

            @Test
            @Order(3)
            void nestedTest1_3() {
            }

            @Nested
            @Order(1)
            class FirstDoublyNestedTestCase {
                @Test
                void nestedTest1_1_1() {
                }
            }

            @Nested
            @Order(2)
            class SecondDoublyNestedTestCase {
                @Test
                void nestedTest1_2_1() {
                }
            }

            @Nested
            @Order(3)
            class ThirdDoublyNestedTestCase {
                @Test
                void nestedTest1_3_1() {
                }
            }
        }

        @Nested
        @Order(2)
        class SecondNestedTestCase {
            @Test
            void nestedTest2_1() {
            }
        }

        @Nested
        @Order(3)
        class ThirdNestedTestCase {
            @Test
            void nestedTest3_1() {
            }
        }
    }
}
