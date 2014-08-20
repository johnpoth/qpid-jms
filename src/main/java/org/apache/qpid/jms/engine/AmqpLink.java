/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.qpid.jms.engine;

import org.apache.qpid.proton.engine.Link;

public abstract class AmqpLink extends AmqpResource
{
    private final AmqpConnection _amqpConnection;
    private final AmqpSession _amqpSession;
    private final Link _protonLink;
    private boolean _linkError;

    public AmqpLink(AmqpSession amqpSession, Link protonLink, AmqpConnection amqpConnection)
    {
        _amqpSession = amqpSession;
        _protonLink = protonLink;
        _amqpConnection = amqpConnection;
    }

    public boolean getLinkError()
    {
        return _linkError;
    }

    public void setLinkError()
    {
        _linkError = true;
    }

    AmqpConnection getAmqpConnection()
    {
        return _amqpConnection;
    }

    AmqpSession getAmqpSession()
    {
        return _amqpSession;
    }

    Link getProtonLink()
    {
        return _protonLink;
    }

    @Override
    protected void doOpen()
    {
        _protonLink.open();
    }

    @Override
    protected void doClose()
    {
        _amqpConnection.addPendingCloseLink(_protonLink);
        _protonLink.close();
    }
}
