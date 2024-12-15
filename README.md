# Playright Bug Page.onClose
Demonstrates `Page.onClose` not being fired

## How to run test
```
git clone git@github.com:davidnewcomb/playright-bug-onclose.git
cd playright-bug-onclose
mvn clean install
```

## Demonstrate the bug
1. Run the program: `java -jar target/playright-bug-onclose-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
2. When the browser appears, click the close tab or the 'X' button.
3. Watch how ED-209 counts down to zero long after the window is closed.
4. The program calls Page.close() and the events are fired.

## Expected behaviour
When the page is closed the `onClose` event should fire.
