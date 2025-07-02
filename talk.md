# Building

- Without Buildpacks
Need to have have Graalvm or NIK installed
- Using Buildpacks
Just use a regular JDK but a docker daemon is needed

---

# Advantages

- Reduced CPU Usage
- Reduced Memory Usage
- Reduced Startup Time
- Reduced Attack Surface 

---

# Limitations & Gotchas

- Code that uses reflection or creates dynamic proxies needs to be registered with AOT
- Most spring and third party libraries include native-image hints
- Some of the hints are provided by https://github.com/oracle/graalvm-reachability-metadata
- You can provide your own hints
- glibc version matters
- cpu capabilities matter

---

# Tips & Tricks

- Use the native-image-agent to capture all the metadata
- Pay attention to `native-image` command and output
- Look at https://graalvm.github.io/native-build-tools/latest/maven-plugin.html

---


