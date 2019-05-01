
import java.util.EmptyStackException;

public class PriorityQueue<T extends Comparable<T> & Updatable<T>>
{
	private Node firstNode;
    
	public PriorityQueue()
	{
		firstNode = null;
	}
	
	public void enqueue(T newEntry)
	{
		Node newNode = new Node(newEntry, null);
		
		if (isEmpty())
		{
			firstNode = newNode;
		}
		else
		{
			if (firstNode.getData().compareTo(newNode.getData()) > 0)
			{
				newNode.next = firstNode;
				firstNode = newNode;
				return;
			}
			if (firstNode.getData().compareTo(newNode.getData()) == 0)
			{
				newNode.next = firstNode.next;
				firstNode.next = newNode;
				return;
			}
			for (Node iterator = firstNode; iterator != null; iterator = iterator.next)
			{
				if (iterator.next == null)
				{
					iterator.next = newNode;
					return;
				}
				if (iterator.next.getData().compareTo(newNode.getData()) > 0)
				{
					newNode.next = iterator.next;
					iterator.next = newNode;
					return;
				}
				if (iterator.next.getData().compareTo(newNode.getData()) == 0)
				{
					newNode.next = iterator.next.next;
					iterator.next.next = newNode;
					return;
				}
			}
		}
	}
	
	public void enqueueAfterFront(T newEntry)
	{
		Node newNode = new Node(newEntry, null);
		if (isEmpty())
		{
			firstNode = newNode;
		}
		else
		{
			if (firstNode.getData().compareTo(newNode.getData()) >= 0)
			{
				newNode.next = firstNode.next;
				firstNode.next = newNode;
				return;
			}
			for (Node iterator = firstNode; iterator != null; iterator = iterator.next)
			{
				if (iterator.next == null)
				{
					iterator.next = newNode;
					return;
				}
				if (iterator.next.getData().compareTo(newNode.getData()) > 0)
				{
					newNode.next = iterator.next;
					iterator.next = newNode;
					return;
				}
				if (iterator.next.getData().compareTo(newNode.getData()) == 0)
				{
					newNode.next = iterator.next.next;
					iterator.next.next = newNode;
					return;
				}
			}
		}
	}
	
	public T dequeue()
	{
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		T data = firstNode.data;
		firstNode = firstNode.next;
		return data;
	}
	
    public T getFront()
    {
        if (isEmpty())
            throw new EmptyQueueException("Queue is Empty");
        return firstNode.getData();
    }
	
	public boolean isEmpty()
	{
		return firstNode == null;
	}
	
	public void updateValues()
	{
		for (Node iterator = firstNode; iterator != null; iterator = iterator.next)
		{
			iterator.getData().update();
		}
	}
	
    private class Node
    {
        private T data;
        private Node next;

        private Node(T data, Node next)
        {
            this.data = data;
            this.next = next;
        }

        private T getData()
        {
            return data;
        }

    }
}
