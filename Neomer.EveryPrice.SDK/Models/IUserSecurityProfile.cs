using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
	public interface IUserSecurityProfile : IEntity
	{

		IUser Owner { get; set; }

		string Password { get; set; }

	}
}
