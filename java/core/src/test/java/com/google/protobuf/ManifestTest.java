// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// https://developers.google.com/protocol-buffers/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
package com.google.protobuf;

import static com.google.common.truth.Truth.assertThat;
import java.io.IOException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ManifestTest {

    Attributes attributes;

    @Before
    public void setUp() throws IOException {
        // any class is fine here, but should be present for both core/ and lite/
        Class clazz = CodedInputStream.class;
        String className = clazz.getSimpleName() + ".class";
        String classPath = clazz.getResource(className).toString();
        String manifestPath = classPath.replace("com/google/protobuf/CodedInputStream.class", "META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(new URL(manifestPath).openStream());
        attributes = manifest.getMainAttributes();
    }

    @Test
    public void testManifestVersion() throws IOException {
        String version = attributes.getValue("Manifest-Version");
        assertThat(version).isEqualTo("1.0");
    }

    @Test
    public void testBundleManifestVersion() {
        String version = attributes.getValue("Bundle-ManifestVersion");
        assertThat(version).isEqualTo("2");
    }

    @Test
    public void testBundleSymbolicName() {
        String name = attributes.getValue("Bundle-SymbolicName");
        assertThat(name).isEqualTo("com.google.protobuf");
    }

    @Test
    public void testBundleExport() {
        String export = attributes.getValue("Export-Package");
        assertThat(export).startsWith("com.google.protobuf;version=");
    }

    @Test
    public void testBundleImport() {
        String importPkg = attributes.getValue("Import-Package");
        assertThat(importPkg).contains("sun.misc;resolution:=optional");
    }
}
