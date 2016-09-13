/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.javainterface.exception;

/**
 * io.riddles.javainterface.exception.TerminalException - Created on 6/27/16
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class TerminalException extends Exception {

    private int statusCode = 1;

    public TerminalException(String message) {
        super(message);
    }

    public TerminalException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public void setStatusCode(int s) {
        this.statusCode = s;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}