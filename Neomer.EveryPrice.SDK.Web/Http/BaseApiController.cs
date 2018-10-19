using Neomer.EveryPrice.SDK.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using Neomer.EveryPrice.SDK.Session;
using NHibernate;

namespace Neomer.EveryPrice.SDK.Web.Http
{
	public class BaseApiController : ApiController
	{
		private ISessionProvider _sessionProvider = WebSessionProvider.Instance;

		#region Public methods

		public BaseApiController()
		{
			InitSession();
		}

		#endregion

		#region Private methods

		/// <summary>
		/// Инициализация сессии
		/// </summary>
		private void InitSession()
		{
			SessionUid = Guid.NewGuid();
			Logger.Log.DebugFormat("New session {0}", SessionUid);
			_sessionProvider.Create(SessionUid);
		}

		/// <summary>
		/// Завершение сессии
		/// </summary>
		private void ReleaseSession()
		{

		}

		#endregion

		#region Properties

		/// <summary>
		/// UID сессии
		/// </summary>
		public Guid SessionUid { get; private set; }

		/// <summary>
		/// Сессия для работы с БД
		/// </summary>
		public ISession CurrentSession
		{
			get
			{
				return _sessionProvider != null ? _sessionProvider.GetSession(SessionUid) : null;
			}
		} 

		#endregion

	}
}