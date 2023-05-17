/*******************************************************************************
 * Copyright (C) 2018, OpenRefine contributors
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/

package org.openrefine.model;

import java.util.Arrays;
import java.util.Collections;

import org.openrefine.util.ParsingUtilities;
import org.openrefine.util.TestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ColumnModelTests {

    ColumnModel SUT = new ColumnModel(
            Arrays.asList(
                    new ColumnMetadata("a", "b", null),
                    new ColumnMetadata("c", "d", null)));

    @Test
    public void serializeColumnModel() throws ModelException {
        ColumnModel model = new ColumnModel(
                Arrays.asList(new ColumnMetadata("a"), new ColumnMetadata("b")));
        String json = "{\n" +
                "       \"columns\" : [ {\n" +
                "         \"name\" : \"a\",\n" +
                "         \"originalName\" : \"a\"\n" +
                "       }, {\n" +
                "         \"name\" : \"b\",\n" +
                "         \"originalName\" : \"b\"\n" +
                "       } ],\n" +
                "       \"keyCellIndex\" : 0,\n" +
                "       \"keyColumnName\" : \"a\",\n" +
                "       \"hasRecords\": false\n" +
                "     }";
        TestUtils.isSerializedTo(model, json, ParsingUtilities.defaultWriter);
    }

    @Test
    public void serializeColumnModelEmpty() {
        String json = "{"
                + "\"columns\":[]," +
                " \"hasRecords\": false"
                + "}";
        ColumnModel m = new ColumnModel(Collections.emptyList());
        TestUtils.isSerializedTo(m, json, ParsingUtilities.defaultWriter);
    }

    @Test
    public void testMerge() {
        ColumnModel columnModelB = new ColumnModel(
                Arrays.asList(
                        new ColumnMetadata("e", "f", null),
                        new ColumnMetadata("g", "h", null)));
        ColumnModel expected = new ColumnModel(
                Arrays.asList(
                        new ColumnMetadata("a", "b", null),
                        new ColumnMetadata("c", "d", null)));

        Assert.assertEquals(SUT.merge(columnModelB), expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMergeIncompatibleNumberOfColumns() {
        ColumnModel columnModel = new ColumnModel(
                Arrays.asList(new ColumnMetadata("a", "b", null)));
        SUT.merge(columnModel);
    }
}