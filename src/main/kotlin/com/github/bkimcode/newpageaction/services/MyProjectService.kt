package com.github.bkimcode.newpageaction.services

import com.intellij.openapi.project.Project
import com.github.bkimcode.newpageaction.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))

        System.getenv("CI")
    }

    /**
     * Chosen by fair dice roll, guaranteed to be random.
     */
    fun getRandomNumber() = 4
}
