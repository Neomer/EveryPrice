using Neomer.EveryPrice.SDK.Core;
using Neomer.EveryPrice.SDK.Exceptions.Security;
using Neomer.EveryPrice.SDK.Helpers;
using NHibernate;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Session
{
	public class WebSessionProvider : Singleton<WebSessionProvider, ISessionProvider>, ISessionProvider
	{
		IDictionary<Guid, ISession> _sessions;

		private WebSessionProvider()
		{
			_sessions = new Dictionary<Guid, ISession>();
		}

		public ISession GetSession(Guid sessionUid)
		{
			return _sessions[sessionUid];
		}

		public ISession Create(Guid sessionUid)
		{
			if (_sessions.ContainsKey(sessionUid))
			{
				throw new DuplicateSessionKeyException();
			}
			ISession session = NHibernateHelper.OpenSession();
			_sessions.Add(sessionUid, session);
			return session as ISession;
		}

		public void Release(Guid sessionUid)
		{
			if (_sessions.ContainsKey(sessionUid))
			{
				var session = _sessions[sessionUid];
				session.Close();
				_sessions.Remove(sessionUid);
			}
		}
	}
}
