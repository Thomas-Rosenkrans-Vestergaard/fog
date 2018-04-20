package tvestergaard.fog.presentation;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class NotificationTest
{
    @Test
    public void info() throws Exception
    {
        String       message      = UUID.randomUUID().toString();
        Notification notification = Notification.info(message);
        assertEquals(message, notification.getMessage());
        assertEquals(Notification.Level.INFO, notification.getLevel());
    }

    @Test
    public void success() throws Exception
    {
        String       message      = UUID.randomUUID().toString();
        Notification notification = Notification.success(message);
        assertEquals(message, notification.getMessage());
        assertEquals(Notification.Level.SUCCESS, notification.getLevel());
    }

    @Test
    public void warning() throws Exception
    {
        String       message      = UUID.randomUUID().toString();
        Notification notification = Notification.warning(message);
        assertEquals(message, notification.getMessage());
        assertEquals(Notification.Level.WARNING, notification.getLevel());
    }

    @Test
    public void error() throws Exception
    {
        String       message      = UUID.randomUUID().toString();
        Notification notification = Notification.error(message);
        assertEquals(message, notification.getMessage());
        assertEquals(Notification.Level.ERROR, notification.getLevel());
    }

    @Test
    public void of() throws Exception
    {
        for (Notification.Level level : Notification.Level.values()) {
            String       message      = UUID.randomUUID().toString();
            Notification notification = Notification.of(message, level);
            assertEquals(message, notification.getMessage());
            assertEquals(level, notification.getLevel());
        }
    }
}