// Copyright 2016 riddles.io (developers@riddles.io)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//  
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package io.riddles.game.io;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Queue;

import io.riddles.game.io.InputStreamGobbler;

/**
 * IOWrapper class
 * 
 * A wrapper that handles communication between a process and
 * the game wrapper
 * 
 * @author Sid Mijnders <sid@riddles.io>, Jim van Eeden <jim@starapple.nl>
 */
public class IOWrapper implements Runnable {
    
    private Process process;
    private OutputStreamWriter inputStream;
    private InputStreamGobbler outputGobbler;
    private InputStreamGobbler errorGobbler;
    protected boolean finished;
    
    public String response;
    public Queue<String> inputQueue;
    
    public IOWrapper(Process process) {
        this.inputStream = new OutputStreamWriter(process.getOutputStream());
        this.outputGobbler = new InputStreamGobbler(process.getInputStream(), this, "output");
        this.errorGobbler = new InputStreamGobbler(process.getErrorStream(), this, "error");
        this.process = process;
        this.finished = false;
    }
    
    /**
     * Sends a line to the process
     * @param line Output line
     * @return True if write was successful, false otherwise
     */
    public boolean write(String line) {
    	System.out.println(line);
        if (this.finished) return false;

        try {
            this.inputStream.write(line + "\n");
            this.inputStream.flush();
        } catch(IOException e) {
            System.err.println("Writing to inputstream failed.");
            finish();
            return false;
        }

        return true;
    }

    /**
     * Sends a line to the process and gets response
     * @param line Output line
     * @param timeout Time the process has to respond
     * @return Response from process
     * @throws IOException
     */
    public String ask(String line, long timeout) throws IOException {
        if (write(line)) {
            return getResponse(timeout);
        }
        return "";
    }

    /**
     * Waits until process returns a response and returns it
     * @param timeout Time before timeout
     * @return Process's response
     */
    public String getResponse(long timeout) {
        long timeStart = System.currentTimeMillis();
        String response;

        while (this.response == null) {
            long timeNow = System.currentTimeMillis();
            long timeElapsed = timeNow - timeStart;
            
            if (timeElapsed >= timeout) {
                return handleResponseTimeout(timeout);
            }

            try { 
                Thread.sleep(2);
            } catch (InterruptedException e) {}
        }
        
        if (this.inputQueue != null) {
            this.inputQueue.remove(this.response);
        }

        response = this.response;
        this.response = null;
        
        return response;
    }

    /**
     * Reponse when there is a timeout
     * @param timeout Time before timeout
     * @return Response
     */
    protected String handleResponseTimeout(long timeout) {
        return "";
    }
    
    /**
     * Ends the process and it's communication
     */
    protected void finish() {
        if (this.finished)
            return;

        // stop io streams
        try { this.inputStream.close(); } catch (IOException e) {}
        this.outputGobbler.finish();
        this.errorGobbler.finish();
        
        // end the process
        this.process.destroy();
        try { this.process.waitFor(); } catch (InterruptedException ex) {}

        this.finished = true;
    }
    
    /**
     * @return The process
     */
    public Process getProcess() {
        return this.process;
    }
    
    /**
     * @return The complete stdOut of the process
     */
    public String getStdout() {
        return this.outputGobbler.getData();
    }
    
    /**
     * @return The complete stdErr from the process
     */
    public String getStderr() {
        return this.errorGobbler.getData();
    }

    @Override
    /**
     * Start the communication with the process
     */
    public void run() {
        this.outputGobbler.start();
        this.errorGobbler.start();
    }
}