using NHibernate;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Session
{
	public interface ISessionProvider
	{
		ISession GetSession(Guid sessionUid);

		ISession Create(Guid sessionUid);

		void Release(Guid sessionUid);
	}
}
