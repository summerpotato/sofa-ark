/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.ark.container.service.session;

import com.alipay.sofa.ark.container.BaseTest;
import com.alipay.sofa.ark.container.registry.ContainerServiceProvider;
import com.alipay.sofa.ark.container.service.ArkServiceContainer;
import com.alipay.sofa.ark.container.service.ArkServiceContainerHolder;
import com.alipay.sofa.ark.container.session.handler.ArkCommandHandler;
import com.alipay.sofa.ark.spi.service.registry.RegistryService;
import com.alipay.sofa.ark.spi.service.session.CommandProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author qilong.zql
 * @since 0.4.0
 */
public class CommandHandlerTest extends BaseTest {

    private ArkServiceContainer arkServiceContainer = new ArkServiceContainer();

    @Before
    public void before() {
        arkServiceContainer.start();
    }

    @After
    public void after() {
        arkServiceContainer.stop();
    }

    @Test
    public void test() {
        ArkCommandHandler arkCommandHandler = new ArkCommandHandler();
        RegistryService registryService = ArkServiceContainerHolder.getContainer().getService(
            RegistryService.class);
        registryService.publishService(CommandProvider.class, new MockCommandProvider(),
            new ContainerServiceProvider());

        Assert.assertTrue("mock help".equals(arkCommandHandler.handleCommand("any")));
        Assert.assertTrue("mock command provider".equals(arkCommandHandler.handleCommand("mock")));
    }

    public class MockCommandProvider implements CommandProvider {

        @Override
        public String getHelp() {
            return "mock help";
        }

        @Override
        public String getHelp(String commandMarker) {
            return "mock help";
        }

        @Override
        public boolean validate(String command) {
            return command.contains("mock");
        }

        @Override
        public String handleCommand(String command) {
            return "mock command provider";
        }
    }

}