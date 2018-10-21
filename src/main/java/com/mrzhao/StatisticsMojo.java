package com.mrzhao;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * 统计项目中的java文件个数
 *
 * @goal touch
 * @phase process-sources
 */
@Mojo(name = "count", defaultPhase = LifecyclePhase.PACKAGE)
public class StatisticsMojo extends AbstractMojo {

    /**
     * 项目根路径
     **/
    @Parameter(property = "basePath")
    private String basePath;

    /**
     * 要统计的文件类型
     **/
    @Parameter(property = "type", defaultValue = "java")
    private String type;

    /**
     * 统计文件的数量
     **/
    private Integer number = 0;

    public void execute() throws MojoExecutionException {
        System.out.println("项目路径为：" + basePath);

        //开始统计
        getFileCount(basePath);

        System.out.println("项目中的" + type + "类型的文件个数为：" + number);
    }

    private void getFileCount(String path) {
        File projectFile = new File(path);
        //获取到项目中下的所有文件
        File[] AllFiles = projectFile.listFiles();
        if (AllFiles == null || AllFiles.length == 0) {
            return;
        }
        //遍历每个文件，判断是不是文件夹，是则在进入递归
        for (File everyFile : AllFiles) {
            if (everyFile.isDirectory()) {
                if ("target".equals(everyFile.getName())) {
                    break;
                }
                getFileCount(everyFile.getAbsolutePath());
            } else {
                //是一个文件，判断是不是java文件
                String fileName = everyFile.getName();
                if (type.equals(fileName.substring(fileName.lastIndexOf(".") + 1))) {
                    number++;
                }
            }
        }
    }
}
