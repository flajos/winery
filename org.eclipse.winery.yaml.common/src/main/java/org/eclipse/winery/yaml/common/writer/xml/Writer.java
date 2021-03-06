/********************************************************************************
 * Copyright (c) 2017 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache Software License 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 *******************************************************************************/
package org.eclipse.winery.yaml.common.writer.xml;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.eclipse.winery.model.tosca.TDefinitions;
import org.eclipse.winery.yaml.common.writer.xml.support.AnonymousPropertiesList;
import org.eclipse.winery.yaml.common.writer.xml.support.PropertiesList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Writer {
    public void writeXML(TDefinitions definitions, Path filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TDefinitions.class, PropertiesList.class, AnonymousPropertiesList.class);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
            @Override
            public String getPreferredPrefix(String s, String s1, boolean b) {
                return "tosca";
            }
        });
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        marshaller.marshal(definitions, filePath.toFile());
    }
}
