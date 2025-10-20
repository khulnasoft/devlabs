package ksoft.language_server_pb

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
import io.grpc.kotlin.ClientCalls.serverStreamingRpc
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls
import io.grpc.kotlin.ServerCalls.serverStreamingServerMethodDefinition
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlinx.coroutines.flow.Flow
import ksoft.language_server_pb.LanguageServerServiceGrpc.getServiceDescriptor

/**
 * Holder for Kotlin coroutine-based client and server APIs for
 * ksoft.language_server_pb.LanguageServerService.
 */
public object LanguageServerServiceGrpcKt {
  public const val SERVICE_NAME: String = LanguageServerServiceGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = LanguageServerServiceGrpc.getServiceDescriptor()

  public val getCompletionsMethod:
      MethodDescriptor<LanguageServer.GetCompletionsRequest, LanguageServer.GetCompletionsResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getGetCompletionsMethod()

  public val acceptCompletionMethod:
      MethodDescriptor<LanguageServer.AcceptCompletionRequest, LanguageServer.AcceptCompletionResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getAcceptCompletionMethod()

  public val heartbeatMethod:
      MethodDescriptor<LanguageServer.HeartbeatRequest, LanguageServer.HeartbeatResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getHeartbeatMethod()

  public val getAuthTokenMethod:
      MethodDescriptor<LanguageServer.GetAuthTokenRequest, LanguageServer.GetAuthTokenResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getGetAuthTokenMethod()

  public val recordEventMethod:
      MethodDescriptor<LanguageServer.RecordEventRequest, LanguageServer.RecordEventResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getRecordEventMethod()

  public val cancelRequestMethod:
      MethodDescriptor<LanguageServer.CancelRequestRequest, LanguageServer.CancelRequestResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getCancelRequestMethod()

  public val exitMethod: MethodDescriptor<LanguageServer.ExitRequest, LanguageServer.ExitResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getExitMethod()

  public val getProcessesMethod:
      MethodDescriptor<LanguageServer.GetProcessesRequest, LanguageServer.GetProcessesResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getGetProcessesMethod()

  public val getChatMessageMethod:
      MethodDescriptor<LanguageServer.GetChatMessageRequest, LanguageServer.GetChatMessageResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getGetChatMessageMethod()

  public val recordChatFeedbackMethod:
      MethodDescriptor<LanguageServer.RecordChatFeedbackRequest, LanguageServer.RecordChatFeedbackResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getRecordChatFeedbackMethod()

  public val recordChatPanelSessionMethod:
      MethodDescriptor<LanguageServer.RecordChatPanelSessionRequest, LanguageServer.RecordChatPanelSessionResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getRecordChatPanelSessionMethod()

  public val clusteredSearchMethod:
      MethodDescriptor<LanguageServer.ClusteredSearchRequest, LanguageServer.ClusteredSearchResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getClusteredSearchMethod()

  public val exactSearchMethod:
      MethodDescriptor<LanguageServer.ExactSearchRequest, LanguageServer.ExactSearchResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getExactSearchMethod()

  public val addTrackedWorkspaceMethod:
      MethodDescriptor<LanguageServer.AddTrackedWorkspaceRequest, LanguageServer.AddTrackedWorkspaceResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getAddTrackedWorkspaceMethod()

  public val removeTrackedWorkspaceMethod:
      MethodDescriptor<LanguageServer.RemoveTrackedWorkspaceRequest, LanguageServer.RemoveTrackedWorkspaceResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getRemoveTrackedWorkspaceMethod()

  public val refreshContextForIdeActionMethod:
      MethodDescriptor<LanguageServer.RefreshContextForIdeActionRequest, LanguageServer.RefreshContextForIdeActionResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getRefreshContextForIdeActionMethod()

  public val getFunctionsMethod:
      MethodDescriptor<LanguageServer.GetFunctionsRequest, LanguageServer.GetFunctionsResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getGetFunctionsMethod()

  public val getUserStatusMethod:
      MethodDescriptor<LanguageServer.GetUserStatusRequest, LanguageServer.GetUserStatusResponse>
    @JvmStatic
    get() = LanguageServerServiceGrpc.getGetUserStatusMethod()

  /**
   * A stub for issuing RPCs to a(n) ksoft.language_server_pb.LanguageServerService service as
   * suspending coroutines.
   */
  @StubFor(LanguageServerServiceGrpc::class)
  public class LanguageServerServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<LanguageServerServiceCoroutineStub>(channel, callOptions) {
    public override fun build(channel: Channel, callOptions: CallOptions):
        LanguageServerServiceCoroutineStub = LanguageServerServiceCoroutineStub(channel,
        callOptions)

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
    public suspend fun getCompletions(request: LanguageServer.GetCompletionsRequest,
        headers: Metadata = Metadata()): LanguageServer.GetCompletionsResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getGetCompletionsMethod(),
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
    public suspend fun acceptCompletion(request: LanguageServer.AcceptCompletionRequest,
        headers: Metadata = Metadata()): LanguageServer.AcceptCompletionResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getAcceptCompletionMethod(),
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
    public suspend fun heartbeat(request: LanguageServer.HeartbeatRequest, headers: Metadata =
        Metadata()): LanguageServer.HeartbeatResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getHeartbeatMethod(),
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
    public suspend fun getAuthToken(request: LanguageServer.GetAuthTokenRequest, headers: Metadata =
        Metadata()): LanguageServer.GetAuthTokenResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getGetAuthTokenMethod(),
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
    public suspend fun recordEvent(request: LanguageServer.RecordEventRequest, headers: Metadata =
        Metadata()): LanguageServer.RecordEventResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getRecordEventMethod(),
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
    public suspend fun cancelRequest(request: LanguageServer.CancelRequestRequest, headers: Metadata
        = Metadata()): LanguageServer.CancelRequestResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getCancelRequestMethod(),
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
    public suspend fun exit(request: LanguageServer.ExitRequest, headers: Metadata = Metadata()):
        LanguageServer.ExitResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getExitMethod(),
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
    public suspend fun getProcesses(request: LanguageServer.GetProcessesRequest, headers: Metadata =
        Metadata()): LanguageServer.GetProcessesResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getGetProcessesMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Returns a [Flow] that, when collected, executes this RPC and emits responses from the
     * server as they arrive.  That flow finishes normally if the server closes its response with
     * [`Status.OK`][Status], and fails by throwing a [StatusException] otherwise.  If
     * collecting the flow downstream fails exceptionally (including via cancellation), the RPC
     * is cancelled with that exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return A flow that, when collected, emits the responses from the server.
     */
    public fun getChatMessage(request: LanguageServer.GetChatMessageRequest, headers: Metadata =
        Metadata()): Flow<LanguageServer.GetChatMessageResponse> = serverStreamingRpc(
      channel,
      LanguageServerServiceGrpc.getGetChatMessageMethod(),
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
    public suspend fun recordChatFeedback(request: LanguageServer.RecordChatFeedbackRequest,
        headers: Metadata = Metadata()): LanguageServer.RecordChatFeedbackResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getRecordChatFeedbackMethod(),
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
    public suspend fun recordChatPanelSession(request: LanguageServer.RecordChatPanelSessionRequest,
        headers: Metadata = Metadata()): LanguageServer.RecordChatPanelSessionResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getRecordChatPanelSessionMethod(),
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
    public suspend fun clusteredSearch(request: LanguageServer.ClusteredSearchRequest,
        headers: Metadata = Metadata()): LanguageServer.ClusteredSearchResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getClusteredSearchMethod(),
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
    public suspend fun exactSearch(request: LanguageServer.ExactSearchRequest, headers: Metadata =
        Metadata()): LanguageServer.ExactSearchResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getExactSearchMethod(),
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
    public suspend fun addTrackedWorkspace(request: LanguageServer.AddTrackedWorkspaceRequest,
        headers: Metadata = Metadata()): LanguageServer.AddTrackedWorkspaceResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getAddTrackedWorkspaceMethod(),
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
    public suspend fun removeTrackedWorkspace(request: LanguageServer.RemoveTrackedWorkspaceRequest,
        headers: Metadata = Metadata()): LanguageServer.RemoveTrackedWorkspaceResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getRemoveTrackedWorkspaceMethod(),
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
    public suspend
        fun refreshContextForIdeAction(request: LanguageServer.RefreshContextForIdeActionRequest,
        headers: Metadata = Metadata()): LanguageServer.RefreshContextForIdeActionResponse =
        unaryRpc(
      channel,
      LanguageServerServiceGrpc.getRefreshContextForIdeActionMethod(),
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
    public suspend fun getFunctions(request: LanguageServer.GetFunctionsRequest, headers: Metadata =
        Metadata()): LanguageServer.GetFunctionsResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getGetFunctionsMethod(),
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
    public suspend fun getUserStatus(request: LanguageServer.GetUserStatusRequest, headers: Metadata
        = Metadata()): LanguageServer.GetUserStatusResponse = unaryRpc(
      channel,
      LanguageServerServiceGrpc.getGetUserStatusMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the ksoft.language_server_pb.LanguageServerService service based on
   * Kotlin coroutines.
   */
  public abstract class LanguageServerServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.GetCompletions.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getCompletions(request: LanguageServer.GetCompletionsRequest):
        LanguageServer.GetCompletionsResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.GetCompletions is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.AcceptCompletion.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun acceptCompletion(request: LanguageServer.AcceptCompletionRequest):
        LanguageServer.AcceptCompletionResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.AcceptCompletion is unimplemented"))

    /**
     * Returns the response to an RPC for ksoft.language_server_pb.LanguageServerService.Heartbeat.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun heartbeat(request: LanguageServer.HeartbeatRequest):
        LanguageServer.HeartbeatResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.Heartbeat is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.GetAuthToken.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getAuthToken(request: LanguageServer.GetAuthTokenRequest):
        LanguageServer.GetAuthTokenResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.GetAuthToken is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.RecordEvent.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun recordEvent(request: LanguageServer.RecordEventRequest):
        LanguageServer.RecordEventResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.RecordEvent is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.CancelRequest.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun cancelRequest(request: LanguageServer.CancelRequestRequest):
        LanguageServer.CancelRequestResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.CancelRequest is unimplemented"))

    /**
     * Returns the response to an RPC for ksoft.language_server_pb.LanguageServerService.Exit.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun exit(request: LanguageServer.ExitRequest): LanguageServer.ExitResponse =
        throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.Exit is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.GetProcesses.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getProcesses(request: LanguageServer.GetProcessesRequest):
        LanguageServer.GetProcessesResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.GetProcesses is unimplemented"))

    /**
     * Returns a [Flow] of responses to an RPC for
     * ksoft.language_server_pb.LanguageServerService.GetChatMessage.
     *
     * If creating or collecting the returned flow fails with a [StatusException], the RPC
     * will fail with the corresponding [Status].  If it fails with a
     * [java.util.concurrent.CancellationException], the RPC will fail with status
     * `Status.CANCELLED`.  If creating
     * or collecting the returned flow fails for any other reason, the RPC will fail with
     * `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open fun getChatMessage(request: LanguageServer.GetChatMessageRequest):
        Flow<LanguageServer.GetChatMessageResponse> = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.GetChatMessage is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.RecordChatFeedback.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun recordChatFeedback(request: LanguageServer.RecordChatFeedbackRequest):
        LanguageServer.RecordChatFeedbackResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.RecordChatFeedback is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.RecordChatPanelSession.
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
        fun recordChatPanelSession(request: LanguageServer.RecordChatPanelSessionRequest):
        LanguageServer.RecordChatPanelSessionResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.RecordChatPanelSession is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.ClusteredSearch.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun clusteredSearch(request: LanguageServer.ClusteredSearchRequest):
        LanguageServer.ClusteredSearchResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.ClusteredSearch is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.ExactSearch.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun exactSearch(request: LanguageServer.ExactSearchRequest):
        LanguageServer.ExactSearchResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.ExactSearch is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.AddTrackedWorkspace.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun addTrackedWorkspace(request: LanguageServer.AddTrackedWorkspaceRequest):
        LanguageServer.AddTrackedWorkspaceResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.AddTrackedWorkspace is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.RemoveTrackedWorkspace.
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
        fun removeTrackedWorkspace(request: LanguageServer.RemoveTrackedWorkspaceRequest):
        LanguageServer.RemoveTrackedWorkspaceResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.RemoveTrackedWorkspace is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.RefreshContextForIdeAction.
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
        fun refreshContextForIdeAction(request: LanguageServer.RefreshContextForIdeActionRequest):
        LanguageServer.RefreshContextForIdeActionResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.RefreshContextForIdeAction is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.GetFunctions.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getFunctions(request: LanguageServer.GetFunctionsRequest):
        LanguageServer.GetFunctionsResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.GetFunctions is unimplemented"))

    /**
     * Returns the response to an RPC for
     * ksoft.language_server_pb.LanguageServerService.GetUserStatus.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [Status].  If this method fails with a [java.util.concurrent.CancellationException], the RPC
     * will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun getUserStatus(request: LanguageServer.GetUserStatusRequest):
        LanguageServer.GetUserStatusResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method ksoft.language_server_pb.LanguageServerService.GetUserStatus is unimplemented"))

    public final override fun bindService(): ServerServiceDefinition =
        builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getGetCompletionsMethod(),
      implementation = ::getCompletions
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getAcceptCompletionMethod(),
      implementation = ::acceptCompletion
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getHeartbeatMethod(),
      implementation = ::heartbeat
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getGetAuthTokenMethod(),
      implementation = ::getAuthToken
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getRecordEventMethod(),
      implementation = ::recordEvent
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getCancelRequestMethod(),
      implementation = ::cancelRequest
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getExitMethod(),
      implementation = ::exit
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getGetProcessesMethod(),
      implementation = ::getProcesses
    ))
      .addMethod(serverStreamingServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getGetChatMessageMethod(),
      implementation = ::getChatMessage
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getRecordChatFeedbackMethod(),
      implementation = ::recordChatFeedback
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getRecordChatPanelSessionMethod(),
      implementation = ::recordChatPanelSession
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getClusteredSearchMethod(),
      implementation = ::clusteredSearch
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getExactSearchMethod(),
      implementation = ::exactSearch
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getAddTrackedWorkspaceMethod(),
      implementation = ::addTrackedWorkspace
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getRemoveTrackedWorkspaceMethod(),
      implementation = ::removeTrackedWorkspace
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getRefreshContextForIdeActionMethod(),
      implementation = ::refreshContextForIdeAction
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getGetFunctionsMethod(),
      implementation = ::getFunctions
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = LanguageServerServiceGrpc.getGetUserStatusMethod(),
      implementation = ::getUserStatus
    )).build()
  }
}
