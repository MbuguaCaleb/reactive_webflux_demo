**Reactive Notes**

```aidl

n webFlux we do not keep our threads in waiting state
This is a very big advantage we have when using webFlux.
It will send the request and will not wait for the response to come back
It will be doing some other task in the meantime.

In webflux if i send a request,my threads to not wait for the response to come back
but instead can be doing someother task.

A webflux application is therefore non-blocking

In a servelet application if i have three threads, they will wait uniti the response comes back
before doing anything else,

It therefore means that if i send incoming requests i have to wait,

For webflux it will not wait for the response to come back back.It come be doing some other task as it waits


A webFlux can therefore process a lot more requests


Thread concept comes into practice when many people are connecting to the microservices at the same time


Reactive Manifesto

Responsive
Resilient(Staying responsive incase of failure)
Elastic(staying responsive incase of varying Loads)
Message Driven(Async and Non-Blocking BackPressure)


Netty is able to scale up very well even with a little number of threads

Even Group Pull

(Boss Thread Group)


It keeps itself very busy
The Boss Thread Group

(Worker Thread Bull)



```

**Simple Math Service**

```aidl

1.Find the Square of a given Number(Return a Single Value)

2.Multiplication Table (List)


In a reactive approach,the Single Value will be returned by a Mono

And Many Values will be a returned By A fLUX
```

**Spring boot Structure**

```aidl


(a)dto class---->It is used for returning responses.


```

**Back Pressure**
```aidl
The backend does not do any unccessary work

It is a very important concept of reactive programming.

When using back pressure i only receive values when i need
them...

When my sibscriber cancels the request, the publisher no longer
sends the request.

This is a very efficient use of my Threads

```


**MediaType-EventStream**

```aidl


When i do not specify the MediaType=eventStream, everything is collected
in  a single Mono and returned to me at the end of the Stream.


```


**Bad-Coding Practice**

```aidl
We should ensure that we do everything inside the pipeline to avoid blocking our code

//    List<Response> list = IntStream.rangeClosed(1,10)
//                .peek(i->SleepUtil.sleepSeconds(1))
//                .peek(i-> System.out.println("math-service-processing :" + i))
//                .mapToObj(i-> new Response(i*input))
//                .collect(Collectors.toList());
//
//        return Flux.fromIterable(list);
        
```

**Post Request**

```aidl

I can also read the request in a Non-Blocking Way


```


**Exception handling**

```aidl

The best way to handle spring boot exceptions is separately via the controller 
advice.

This code is way more cleaner.


```