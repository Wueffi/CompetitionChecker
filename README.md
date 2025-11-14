# Completion Checker
A tool to help verify your connect 4 bots

---

# Instructions
1. Run the command `/selectbot <name>` then left click the bottom north-west corner block. `<name>` is the identifier of this build, which you will need to test it.

ex: `/selectbot my_bot`, then click this block:

<img width="860" height="651" alt="image" src="https://github.com/user-attachments/assets/cdb01436-e15c-4845-8275-c973dccbdf4b" />

Once the selection is made, you do not have to re-run this command.

2. Run the command `/verify <name> random <count>`. `<name>` is the same identifier you used in step 1, and `<count>` is the number of tests you want to run.

This command will generate a valid connect 4 board, then place the appropriate redstone blocks in front of the repeaters. 600 ticks later, it verifies that the output is a valid move.

A tick sprint is automatically run to speed through these random tests.

---

At the end, you will get a summary of what tests passed, and which ones failed. To re-run a test (without a sprint), click on the test in chat.

Every time a test is run, the associated board will be output in the minecraft console.