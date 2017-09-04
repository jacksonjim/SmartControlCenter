# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

project(smartHome)

# 设置源文件

# Specifies a path to native header files.
include_directories(src/main/cpp/include)

#相关头文件的目录
#INCLUDE_DIRECTORIES(src/main/cpp/include src/main/cpp/include)

# 查找当前目录下的所有源文件
# 并将名称保存到 DIR_SRCS 变量
#aux_source_directory(src/main/cpp DIR_SRCS)
#add_executable(smartHome ${DIR_SRCS})
# 添加 math 子目录
#add_subdirectory(src/main/cpp/include)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add.library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

file(GLOB cpp_source_files "src/main/cpp/*.*")
file(GLOB jni_source_files "src/main/jni/*.c")

add_library( # Specifies the name of the library.
             smartHome

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ${cpp_source_files} ${jni_source_files})


find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log
              android
              )

 target_link_libraries( # Specifies the target library.
                       smartHome
                       android
                       ${log-lib}

                       # Links the target library to the log library
                       # included in the NDK.
                       )
