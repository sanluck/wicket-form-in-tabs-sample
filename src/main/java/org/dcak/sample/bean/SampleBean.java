/*
 * Copyright 2016 Lukyanov A.A. <sanluck@mail.ru>.
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
package org.dcak.sample.bean;

import java.io.Serializable;

/**
 *
 * @author Lukyanov A.A. <sanluck@mail.ru>
 */
public class SampleBean implements Serializable{
    String id;
    String value;

    public SampleBean() {
    }
    
    public SampleBean(String id, String value) {
        this.id = id;
        this.value = value;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
