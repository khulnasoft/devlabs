package ksoft.chat_web_server_pb

import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import ksoft.chat_web_server_pb.ChatWebServerServiceGrpc.getServiceDescriptor

/**
 * Holder for Kotlin coroutine-based client and server APIs for
 * ksoft.chat_web_server_pb.ChatWebServerService.
 */
public object ChatWebServerServiceGrpcKt {
  public const val SERVICE_NAME: String = ChatWebServerServiceGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = ChatWebServerServiceGrpc.getServiceDescriptor()

  public val getMatchingIndexedReposMethod:
      MethodDescriptor<ChatWebServer.GetMatchingIndexedReposRequest, ChatWebServer.GetMatchingIndexedReposResponse>
    @JvmStatic
    get() = ChatWebServerServiceGrpc.getGetMatchingIndexedReposMethod()

  public val getMatchingCodeContextMethod:
      MethodDescriptor<ChatWebServer.GetMatchingCodeContextRequest, ChatWebServer.GetMatchingCodeContextResponse>
    @JvmStatic
    get() = ChatWebServerServiceGrpc.getGetMatchingCodeContextMethod()

  /**
   * A stub for issuing RPCs to a(n) ksoft.chat_web_server_pb.ChatWebServerService service as
   * suspending coroutines.
   */
  @StubFor(ChatWebServerServiceGrpc::class)
  public class ChatWebServerServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<ChatWebServerServiceCoroutineStub>(channel, callOptions) {
    public override fun build(channel: Channel, callOptions: CallOptions):
        ChatWebServerServiceCoroutineStub = ChatWebServerServiceCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend
        fun getMatchingIndexedRepos(request: ChatWebServer.GetMatchingIndexedReposRequest,
        headers: Metadata = Metadata()): ChatWebServer.GetMatchingIndexedReposResponse = unaryRpc(
      channel,
      ChatWebServerServiceGrpc.getGetMatchingIndexedReposMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][Status].  If the RPC completes with another status, a corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun getMatchingCodeContext(request: ChatWebServer.GetMatchingCodeContextRequest,
        headers: Metadata = Metadata()): ChatWebServer.GetMatchingCodeContextResponse = unaryRpc(
      channel,
      ChatWebServerServiceGrpc.getGetMatchingCodeContextMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the ksoft.chat_web_server_pb.ChatWebServerService service based on
   * Kotlin coroutines.
   */
  public abstract class ChatWebServerServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for
     * ksoft.chat_web_server_pb.ChatWebServerService.GetMatchingIndexedRepos.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend
        fun getMatchingIndexedRepos(request: ChatWebServer.GetMatchingIndexedReposRequest):
        ChatWebServer.GetMatchingIndexedReposResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.chat_web_server_pb.ChatWebServerService.GetMatchingIndexedRepos is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.chat_web_server_pb.ChatWebServerService.GetMatchingCodeContext.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend
        fun getMatchingCodeContext(request: ChatWebServer.GetMatchingCodeContextRequest):
        ChatWebServer.GetMatchingCodeContextResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.chat_web_server_pb.ChatWebServerService.GetMatchingCodeContext is unimplemented"))

    public final override fun bindService(): ServerServiceDefinition =
        builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = ChatWebServerServiceGrpc.getGetMatchingIndexedReposMethod(),
      implementation = ::getMatchingIndexedRepos
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = ChatWebServerServiceGrpc.getGetMatchingCodeContextMethod(),
      implementation = ::getMatchingCodeContext
    )).build()
  }
}
