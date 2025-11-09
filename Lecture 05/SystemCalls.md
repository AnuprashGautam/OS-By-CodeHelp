This is a great question that touches on the fundamentals of **Operating Systems**\! You've provided a table comparing **Windows** and **Unix/Linux** system calls, and you're asking for explanations and code examples, specifically wanting to see the **Linux commands** (which often correspond to the **Unix system calls** listed) execute on your Windows PC.

## System Call Explanations

A **system call** is the programmatic way a computer program requests a service from the operating system's kernel. This is the only way for processes to interact with hardware or other processes in a protected environment.

Here are explanations for the Unix/Linux system calls listed in your table:

### Process Control 🧑‍💻

| Unix System Call | Description |
| :--- | :--- |
| **`fork()`** | Creates a **new process**, which is an **exact copy** of the calling process (the parent). It is the fundamental way to start new tasks in Unix-like systems. |
| **`exit()`** | **Terminates** the calling process. It closes any open files and deallocates memory. An exit status is passed back to the parent process. |
| **`wait()`** | Causes the parent process to **suspend execution** until one of its child processes terminates. It allows the parent to retrieve information about the terminated child. |

-----

### File Management 📁

| Unix System Call | Description |
| :--- | :--- |
| **`open()`** | **Opens** a file for reading, writing, or both. It returns a **file descriptor** (an integer) that is used in subsequent system calls to refer to that file. |
| **`read()`** | **Reads** data from a file (referred to by its file descriptor) into a buffer in the process's memory. |
| **`write()`** | **Writes** data from a buffer in the process's memory to a file (referred to by its file descriptor). |
| **`close()`** | **Deallocates** the file descriptor and releases any kernel resources associated with it. |
| **`chmod()`** | **Changes the permissions** (read, write, execute) of a file or directory. This corresponds to the shell command `chmod`. |
| **`umask()`** | **Sets the file mode creation mask**. This mask is used to disable certain permission bits when a new file is created, ensuring new files don't have overly permissive settings by default. |
| **`chown()`** | **Changes the owner and/or group** of a file. This corresponds to the shell command `chown`. |

-----

### Device Management 💻

| Unix System Call | Description |
| :--- | :--- |
| **`ioctl()`** | (Input/Output Control) A general-purpose system call used for **device-specific I/O operations** that aren't covered by standard `read()` or `write()`. It's often used for configuring terminals or other hardware. |
| **`read()`** | Can also be used to read data from a device (like a terminal or network socket), as devices are often treated as files in Unix. |
| **`write()`** | Can also be used to write data to a device (like a terminal or network socket). |

-----

### Information Management ℹ️

| Unix System Call | Description |
| :--- | :--- |
| **`getpid()`** | **Returns the Process ID (PID)** of the calling process, a unique number identifying it in the system. |
| **`alarm()`** | Sets a **timer** for the calling process. When the timer expires, the kernel sends the `SIGALRM` signal to the process. |
| **`sleep()`** | **Suspends** the execution of the calling process for a specified number of seconds. |

-----

### Communication 📡

| Unix System Call | Description |
| :--- | :--- |
| **`pipe()`** | Creates an **unnamed pipe**, a unidirectional data channel for **Inter-Process Communication (IPC)**. It returns two file descriptors: one for reading and one for writing. |
| **`shmget()`** | (Shared Memory Get) **Allocates a shared memory segment** or gets the ID of an existing one. Shared memory is the fastest form of IPC. |
| **`mmap()`** | (Memory Map) **Maps files or devices into memory**. It's used for efficient file I/O, as well as an advanced form of IPC where different processes can map the same memory region. |

-----

## Running Linux Commands on Windows 🖥️

To see Linux/Unix commands and system calls in action on your Windows PC, you have several excellent options, as the native Windows command prompt ($\text{cmd}$) or PowerShell doesn't support them directly.

The best and most modern ways are:

### 1\. Windows Subsystem for Linux (WSL)

**WSL** is a compatibility layer that allows you to run a full Linux distribution (like Ubuntu or Debian) directly within Windows without the overhead of a traditional virtual machine. It's the ideal way to experience the Linux command line and run Linux binaries.

  * **How to Set Up:**
    1.  Open PowerShell as Administrator.
    2.  Run: `wsl --install`
    3.  Restart your PC if prompted.
    4.  The default distribution (usually Ubuntu) will install and ask you to create a username and password.
    5.  You can now open the **Ubuntu app** (or any other installed distribution) from the Start Menu, and you will be in a **native Linux shell**.

### 2\. Git Bash

If you've installed **Git for Windows**, you also have **Git Bash**. This provides a **Minimalist GNU environment** that includes many common Unix utilities (like `ls`, `grep`, `awk`, `sh`), which are what you are referring to as "Linux commands."

  * **How to Use:** Right-click in any folder in File Explorer and select **"Git Bash Here."**

-----

## Code Examples (Linux/Unix)

Since system calls are executed through programs, here are simple C code examples illustrating how a fundamental system call like **`fork()`** works, which you can compile and run using **WSL** or a similar environment on your Windows PC.

### Example 1: Process Control using `fork()` and `getpid()`

This program demonstrates how a single process splits into two: a **parent** and a **child**.

```c
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

int main() {
    pid_t pid; // Variable to store the process ID

    printf("--- Before fork() ---\n");
    printf("Initial Process ID: %d\n", getpid());

    // Create a new process
    pid = fork();

    if (pid < 0) {
        // Error handling
        fprintf(stderr, "Fork failed!\n");
        return 1;
    } else if (pid == 0) {
        // This code runs in the CHILD process
        printf("--- In Child Process ---\n");
        printf("Child Process ID: %d\n", getpid());
        printf("Child's Parent ID: %d\n", getppid()); // getppid() gets parent's PID
    } else {
        // This code runs in the PARENT process
        // 'pid' holds the ID of the newly created child
        printf("--- In Parent Process ---\n");
        printf("Parent Process ID: %d\n", getpid());
        printf("New Child ID: %d\n", pid);

        // wait() is often used here to wait for the child to finish
        // wait(NULL); 
        // printf("Child process finished.\n");
    }

    printf("--- Process %d exiting ---\n", getpid());
    return 0; // Uses the exit() system call implicitly
}
```

#### **How to Run (using WSL/Git Bash):**

1.  Save the code as a file named `fork_example.c`.
2.  Open your WSL or Git Bash terminal.
3.  **Compile** the code using the GCC compiler:
    ```bash
    gcc fork_example.c -o fork_app
    ```
4.  **Execute** the compiled program:
    ```bash
    ./fork_app
    ```
5.  You will see two distinct sets of output, one for the parent and one for the child, demonstrating the two separate processes executing the same code after the `fork()` call.